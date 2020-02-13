package com.kat.bankaccxmlproc.implementattion;

import com.kat.bankaccxmlproc.entity.Account;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountServiceImplementationTest {

    private AccountServiceImplementation accountServiceImplementation;
    private List<Account> accounts;

    @Before
    public void setUp() {
        accounts = new ArrayList<>();
        accountServiceImplementation = new AccountServiceImplementation(accounts);
    }

    @Test
    public void testShouldReturnTrueWhenOnlyAccountsWithPlnAreSaved() {
        //given
        Account cindy = new Account("PL89109024029112846184748351", "Cindy Lauper", "CZK", 0.00, LocalDate.of(2000, 11, 3));
        Account christopher = new Account("PL59109024024852873896518635", "Christopher Lambert", "PLN", 6000000.00, LocalDate.of(2100, 5, 14));
        Account barbara = new Account("PL06109024024575853497287939", "Barbara Streisand", "RB", 111000.00, LocalDate.of(2001, 4, 29));
        Account tuomas = new Account("PL85109024027852947462921442", "Tuomas Holopainen", "PLN", 0.00, LocalDate.of(2121, 10, 22));

        //when
        accounts.add(cindy);
        accounts.add(christopher);
        accounts.add(barbara);
        accounts.add(tuomas);
        List<Account> expected = Lists.newArrayList(christopher, tuomas);
        List<Account> result = accountServiceImplementation.checkPlnCurrency().addAccountsInOrder();

        //then
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testShouldReturnFalseWhenNotOnlyAccountsWithPlnAreSaved() {
        //given
        Account cindy = new Account("PL89109024029112846184748351", "Cindy Lauper", "CZK", 0.00, LocalDate.of(2000, 11, 3));
        Account christopher = new Account("PL59109024024852873896518635", "Christopher Lambert", "PLN", 6000000.00, LocalDate.of(2100, 5, 14));
        Account barbara = new Account("PL06109024024575853497287939", "Barbara Streisand", "RB", 111000.00, LocalDate.of(2001, 4, 29));
        Account tuomas = new Account("PL85109024027852947462921442", "Tuomas Holopainen", "EU", 0.00, LocalDate.of(2121, 10, 22));

        //when
        accounts.add(cindy);
        accounts.add(christopher);
        accounts.add(barbara);
        accounts.add(tuomas);
        List<Account> expected = Lists.newArrayList(christopher, tuomas); List<Account> result = accountServiceImplementation.checkPlnCurrency().addAccountsInOrder();

        //then
        Assert.assertNotEquals(expected, result);
    }

    @Test
    public void testShouldReturnTrueWhenUnexpiredAccountsAreNotSaved() {
        //given
        Account cindy = new Account("PL89109024029112846184748351", "Cindy Lauper", "CZK", 23.00, LocalDate.now().minusMonths(5));
        Account christopher = new Account("PL59109024024852873896518635", "Christopher Lambert", "PLN", 6000000.00, LocalDate.now().plusMonths(5));
        Account barbara = new Account("PL06109024024575853497287939", "Barbara Streisand", "RB", 111000.00, LocalDate.now().plusMonths(6));
        Account tuomas = new Account("PL85109024027852947462921442", "Tuomas Holopainen", "PLN", 22220.00, LocalDate.now().plusMonths(7));

        //when
        accounts.add(cindy);
        accounts.add(christopher);
        accounts.add(barbara);
        accounts.add(tuomas);
        List<Account> expected = Lists.newArrayList(barbara, christopher, tuomas);
        List<Account> result = accountServiceImplementation.filterExpiredAcc().addAccountsInOrder();

        //then
        Assert.assertEquals(expected, result);
    }


    @Test
    public void testShouldReturnFalseWhenUnexpiredAccountsAreSaved() {
        //given
        Account cindy = new Account("PL89109024029112846184748351", "Cindy Lauper", "CZK", 23.00, LocalDate.now().minusMonths(5));
        Account christopher = new Account("PL59109024024852873896518635", "Christopher Lambert", "PLN", 6000000.00, LocalDate.now().plusMonths(5));
        Account barbara = new Account("PL06109024024575853497287939", "Barbara Streisand", "RB", 111000.00, LocalDate.now().plusMonths(6));
        Account tuomas = new Account("PL85109024027852947462921442", "Tuomas Holopainen", "PLN", 22220.00, LocalDate.now().plusMonths(7));

        //when
        accounts.add(cindy);
        accounts.add(christopher);
        accounts.add(barbara);
        accounts.add(tuomas);
        List<Account> expected = Lists.newArrayList(barbara, christopher, tuomas, cindy);
        List<Account> result = accountServiceImplementation.filterExpiredAcc().addAccountsInOrder();

        //then
        Assert.assertNotEquals(expected, result);
    }


    @Test
    public void testShouldReturnTrueWhenOnlyAccountsWithPositiveAmountOfMoneyAreSaved() {
        //given
        Account cindy = new Account("PL89109024029112846184748351", "Cindy Lauper", "PLN", -20.00, LocalDate.of(2000, 11, 3));
        Account christopher = new Account("PL59109024024852873896518635", "Christopher Lambert", "PLN", 6000000.00, LocalDate.of(2100, 5, 14));
        Account barbara = new Account("PL06109024024575853497287939", "Barbara Streisand", "PLN", -111000.00, LocalDate.of(2001, 4, 29));

        //when
        accounts.add(cindy);
        accounts.add(christopher);
        accounts.add(barbara);
        List<Account> expected = Lists.newArrayList(christopher);
        List<Account> result = accountServiceImplementation.checkIfCurrencyBelowZero().addAccountsInOrder();

        //then
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testShouldReturnFalseWhenAccountsWithNegativeAmountOfMoneyAreSaved() {
        //given
        Account cindy = new Account("PL89109024029112846184748351", "Cindy Lauper", "PLN", -20.00, LocalDate.of(2000, 11, 3));
        Account christopher = new Account("PL59109024024852873896518635", "Christopher Lambert", "PLN", 6000000.00, LocalDate.of(2100, 5, 14));
        Account barbara = new Account("PL06109024024575853497287939", "Barbara Streisand", "PLN", -111000.00, LocalDate.of(2001, 4, 29));

        //when
        accounts.add(cindy);
        accounts.add(christopher);
        accounts.add(barbara);
        List<Account> expected = Lists.newArrayList(christopher, barbara);
        List<Account> result = accountServiceImplementation.checkIfCurrencyBelowZero().addAccountsInOrder();

        //then
        Assert.assertNotEquals(expected, result);
    }

    @Test
    public void testShouldReturnTrueWhileMakingCollectionInOrder() {
        //given
        Account ann = new Account("PL59109024023434693432316989", "Ann Lauper", "PLN", 10340.0, LocalDate.of(2020, 10, 22));
        Account brian = new Account("PL22109024026755788639946237", "Brian Lee", "PLN", 23100.0, LocalDate.of(2021, 5, 11));
        Account claudia = new Account("PL90109024027148144959646164", "Claudia Red", "PLN", 10430.0, LocalDate.of(2021, 6, 13));
        Account derek = new Account("PL36109024022822715835728448", "Derek Proudmore", "PLN", 988330.0, LocalDate.of(2021, 7, 5));

        //when
        accounts.add(ann);
        accounts.add(brian);
        accounts.add(claudia);
        accounts.add(derek);
        List<Account> result = accountServiceImplementation.addAccountsInOrder();

        //then
        Assert.assertEquals(ann, result.get(0));
        Assert.assertEquals(brian, result.get(1));
        Assert.assertEquals(claudia, result.get(2));
        Assert.assertEquals(derek, result.get(3));
    }

    @Test
    public void testShouldReturnFalseWhenCollectionIsNotInOrder() {
        //given
        Account ann = new Account("PL59109024023434693432316989", "Ann Lauper", "PLN", 10340.0, LocalDate.of(2020, 10, 22));
        Account brian = new Account("PL22109024026755788639946237", "Brian Lee", "PLN", 23100.0, LocalDate.of(2021, 5, 11));
        Account claudia = new Account("PL90109024027148144959646164", "Claudia Red", "PLN", 10430.0, LocalDate.of(2021, 6, 13));
        Account derek = new Account("PL36109024022822715835728448", "Derek Proudmore", "PLN", 988330.0, LocalDate.of(2021, 7, 5));

        //when
        accounts.add(ann);
        accounts.add(brian);
        accounts.add(claudia);
        accounts.add(derek);
        List<Account> result = accountServiceImplementation.addAccountsInOrder();

        //then
        Assert.assertNotEquals(brian, result.get(0));
        Assert.assertNotEquals(ann, result.get(1));
        Assert.assertNotEquals(derek, result.get(2));
        Assert.assertNotEquals(claudia, result.get(3));
    }

    @Test
    public void testShouldTrueWhileCheckingIanNumber() {
        //given
        Account ann = new Account("PL5910902402343432316989", "Ann Lauper", "PLN", 100.0, LocalDate.of(2020, 10, 22));
        Account brian = new Account("PL22109788639946237", "Brian Lee", "PLN", 100.0, LocalDate.of(2021, 5, 11));
        Account claudia = new Account("PL90148144959646164", "Claudia Red", "PLN", 100.0, LocalDate.of(2021, 6, 13));
        Account derek = new Account("PL36109024022822715835728448", "Derek Proudmore", "PLN", null, LocalDate.of(2021, 7, 5));

        //when
        accounts.add(ann);
        accounts.add(brian);
        accounts.add(claudia);
        accounts.add(derek);
        List<Account> expected = Lists.newArrayList(derek);
        List<Account> result = accountServiceImplementation.checkIbanNumber().addAccountsInOrder();

        //then
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testShouldFalseWhileCheckingIanNumber() {
        //given
        Account ann = new Account("PL5910902402343432316989", "Ann Lauper", "PLN", 100.0, LocalDate.of(2020, 10, 22));
        Account brian = new Account("PL22109788639946237", "Brian Lee", "PLN", 100.0, LocalDate.of(2021, 5, 11));
        Account claudia = new Account("PL90148144959646164", "Claudia Red", "PLN", 100.0, LocalDate.of(2021, 6, 13));
        Account derek = new Account("PL36109024022822715835728448", "Derek Proudmore", "PLN", null, LocalDate.of(2021, 7, 5));

        //when
        accounts.add(ann);
        accounts.add(brian);
        accounts.add(claudia);
        accounts.add(derek);
        List<Account> expected = Lists.newArrayList(ann, brian, claudia, derek);
        List<Account> result = accountServiceImplementation.checkIbanNumber().addAccountsInOrder();

        //then
        Assert.assertNotEquals(expected, result);
    }
}