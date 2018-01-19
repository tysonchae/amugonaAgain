package me.tyson.accounts;

/**
 * Created by oolong on 2018-01-16.
 */
public class AccountNotFoundException extends RuntimeException {

    Long id;



    public void AccountFoundException(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
