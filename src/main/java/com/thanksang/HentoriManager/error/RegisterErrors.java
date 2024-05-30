package com.thanksang.HentoriManager.error;

public class RegisterErrors extends RuntimeException{
    public RegisterErrors(String messenger){
        super(messenger);
    }

    public RegisterErrors(String messenger, Throwable cause ){
        super(messenger, cause);
    }
}
