package com.kat.bankaccxmlproc.service;


import com.kat.bankaccxmlproc.entity.Account;

import java.util.List;

public interface AccountService {

    AccountService checkPlnCurrency();

    AccountService checkIfCurrencyBelowZero();

    AccountService filterExpiredAcc();

    AccountService checkIbanNumber();

    List<Account> addAccountsInOrder();

}
