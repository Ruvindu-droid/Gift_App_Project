package com.example.gift_app.Model;

public class Users {
    private String name ,phone ,password,BankName,Account;

    public Users()
    {

    }

    public Users(String name, String phone, String password, String BankName, String account) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.Account = account;
        this.BankName = BankName;
        this.Account = account;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        this.Account = account;
    }
}
