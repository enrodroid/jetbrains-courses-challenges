package com.javadev.training.exceptions;

public class FailOperationException extends RuntimeException {
  public FailOperationException() {
    super();
  }
  
  public FailOperationException(String message) {
    super(message);
  }
  
  public FailOperationException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public FailOperationException(Throwable cause) {
    super(cause);
  }
  
  protected FailOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
