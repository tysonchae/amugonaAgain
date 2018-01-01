package me.tyson.accounts;

/**
 * Created by oolong on 2017-12-29.
 */
public class UserDuplicatedException extends RuntimeException {


    String username;

    public UserDuplicatedException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
