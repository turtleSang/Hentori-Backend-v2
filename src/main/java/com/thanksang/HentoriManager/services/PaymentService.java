package com.thanksang.HentoriManager.services;

import com.thanksang.HentoriManager.config.Constance;
import com.thanksang.HentoriManager.entity.AccountEntity;
import com.thanksang.HentoriManager.entity.ReceivableEntity;
import com.thanksang.HentoriManager.entity.RevenueEntity;
import com.thanksang.HentoriManager.error.PaymentErrors;
import com.thanksang.HentoriManager.payload.PaymentOrderRequest;
import com.thanksang.HentoriManager.repository.AccountRepository;
import com.thanksang.HentoriManager.repository.ReceivableRepository;
import com.thanksang.HentoriManager.repository.RevenueRepository;
import com.thanksang.HentoriManager.services.Imp.PaymentServiceImp;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements PaymentServiceImp {
    private RevenueRepository revenueRepository;
    private ReceivableRepository receivableRepository;
    private AccountRepository accountRepository;

    public PaymentService(RevenueRepository revenueRepository, ReceivableRepository receivableRepository, AccountRepository accountRepository) {
        this.revenueRepository = revenueRepository;
        this.receivableRepository = receivableRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void paymentOrder(PaymentOrderRequest paymentOrderRequest) throws IllegalAccessException {
        Constance.checkNullField(paymentOrderRequest);
        Optional<AccountEntity> accountEntity = accountRepository.findById(paymentOrderRequest.getAccountID());
        if (!accountEntity.isPresent()){
            throw new PaymentErrors("account is not exist");
        }
        Optional<ReceivableEntity> receivableEntity = receivableRepository.findById(paymentOrderRequest.getReceivableID());
        if (!receivableEntity.isPresent()){
            throw new PaymentErrors("receivable is not exits");
        }
        if (paymentOrderRequest.getAmount() > receivableEntity.get().getRemaining() || paymentOrderRequest.getAmount() < 0){
            throw new PaymentErrors("Payment can't bigger remaining and must bigger than 0");
        }
      try {
          int payment = receivableEntity.get().getPayment() + paymentOrderRequest.getAmount();
          long balance = accountEntity.get().getBalance() + paymentOrderRequest.getAmount();
          int remaining = receivableEntity.get().getRemaining() - paymentOrderRequest.getAmount();
//        Save revenue entity
          RevenueEntity revenueEntity = new RevenueEntity();
          revenueEntity.setAmount(paymentOrderRequest.getAmount());
          revenueEntity.setAccountEntity(accountEntity.get());
          revenueEntity.setReceivableEntity(receivableEntity.get());
          revenueRepository.save(revenueEntity);
//        update account
          accountEntity.get().setBalance(balance);
          accountRepository.save(accountEntity.get());
//        Update receivable
          receivableEntity.get().setPayment(payment);
          receivableEntity.get().setRemaining(remaining);
          if (remaining == 0){
              receivableEntity.get().setStatus(true);
          }
          receivableRepository.save(receivableEntity.get());
      } catch (Exception e){
          throw new PaymentErrors(e.getMessage(), e.getCause());
      }

    }
}
