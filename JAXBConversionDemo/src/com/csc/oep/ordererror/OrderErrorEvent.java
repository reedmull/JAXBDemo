package com.csc.oep.ordererror;

public class OrderErrorEvent {
    
    private String customerId;
    private String errorCode;
    private String orderId;
    
    public OrderErrorEvent() {
        super();
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
