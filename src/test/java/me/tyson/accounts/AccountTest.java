package me.tyson.accounts;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by tyson on 2017-12-23.
 */
public class AccountTest {

    @Test
    public void getterSetter() {
        Account account = new Account();
        account.setUsername("tyson");
        account.setPassword("password");
        assertThat(account.getUsername(), is("tyson"));
    }
}
