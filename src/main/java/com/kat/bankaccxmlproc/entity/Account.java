package com.kat.bankaccxmlproc.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Account {

    private String iban;
    private String name;
    private String currency;
    private Double balance;
    private LocalDate closingDate;

    public Account() {
    }

    public Account(String iban, String name, String currency, Double balance, LocalDate closingDate) {
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.closingDate = closingDate;
    }
}