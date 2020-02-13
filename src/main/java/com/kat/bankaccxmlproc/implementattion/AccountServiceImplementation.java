package com.kat.bankaccxmlproc.implementattion;

import com.kat.bankaccxmlproc.entity.Account;
import com.kat.bankaccxmlproc.service.AccountService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class AccountServiceImplementation implements AccountService {

    private List<Account> accounts;

    public AccountServiceImplementation(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public AccountService checkPlnCurrency() {

        List<Account> list = new ArrayList<>();
        for (Account account : accounts) {
            if (Optional.ofNullable(account.getCurrency())
                    .orElse("NULL")
                    .equalsIgnoreCase("PLN")) {
                list.add(account);
            }
        }
        this.accounts = list;
        return this;
    }

    @Override
    public AccountService checkIfCurrencyBelowZero() {
        List<Account> accountsList = new ArrayList<>();

        for (Account account: accounts) {
            if (Optional.ofNullable(account.getBalance())
                        .orElse(-1.0) >=0){

                accountsList.add(account);
            }
        }
        this.accounts = accountsList;
        return this;
    }

    @Override
    public AccountService filterExpiredAcc() {
        List<Account> list = new ArrayList<>();
        for (Account account : accounts) {
            if (Optional.ofNullable(account.getClosingDate())
                    .orElse(LocalDate.now().minusMonths(1))
                    .isAfter(LocalDate.now().minusDays(1))) {
                list.add(account);
            }
        }
        this.accounts = list;
        return this;
    }

    @Override
    public AccountService checkIbanNumber() {
        List<Account> list = new ArrayList<>();
        for (Account account : accounts) {
            if (isIbanNumberCorrect(account.getIban())) {
                list.add(account);
            }
        }
        this.accounts = list;
        return this;
    }

    public boolean isIbanNumberCorrect(String ibanNumber){
        if (!digitMatcher(ibanNumber)) {
            return false;
        }
        String first4Chars = ibanNumber.substring(0, 4);
        String number = (ibanNumber.substring(4) + first4Chars).replace("PL", "2521");

        BigDecimal parsedNumber;
        if (isDigit(number)) parsedNumber = new BigDecimal(number);
        else {
            return false;
        }
        BigDecimal remainder = parsedNumber.remainder(BigDecimal.valueOf(97));
        return isControlNumberCorrect(remainder);
    }

    private boolean digitMatcher(String ibanNumber) {
        if (ibanNumber == null) {
            return false;
        }
        boolean regexMatcher = ibanNumber.matches("[A-Z]{2}\\d{26}");
        return regexMatcher;
    }

    private boolean isControlNumberCorrect(BigDecimal remainder) {
        return remainder.equals(BigDecimal.valueOf(1));
    }

    private boolean isDigit(String digit) {
        return digit.chars().allMatch(Character::isDigit);
    }

    @Override
    public List<Account> addAccountsInOrder() {
        List<Account> accList = new ArrayList<>();
        accList.addAll(accounts);
        accList.sort(Comparator
                    .comparing(account -> Optional.ofNullable(account.getName())
                                                    .orElse("ŻŻ")
                                                    .toLowerCase()));
        return accList;
    }
}