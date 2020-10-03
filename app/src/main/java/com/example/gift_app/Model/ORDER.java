package com.example.gift_app.Model;

public class ORDER {
    private String Receiver,ReceiverPhone,ReceiverAddress;

    public ORDER() {
    }


    public ORDER(String receiver, String receiverPhone, String receiverAddress) {
        this.Receiver = receiver;
        this.ReceiverPhone = receiverPhone;
        this.ReceiverAddress = receiverAddress;
    }


    public  String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        this.Receiver = receiver;
    }


    public String getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.ReceiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.ReceiverAddress = receiverAddress;
    }
}
