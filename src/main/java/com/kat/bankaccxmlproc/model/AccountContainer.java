package com.kat.bankaccxmlproc.model;

import com.kat.bankaccxmlproc.entity.Account;

import java.util.List;

public class AccountContainer {

    private List<Account> accounts;

    public AccountContainer(List<Account> accounts) {
        this.accounts = accounts;
    }
}
