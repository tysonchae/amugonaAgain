package me.tyson.accounts;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by tyson on 2017-12-23.
 */

//@Setter @Getter lombok plugin
@Entity
@Setter
@Getter
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String loinId;

    private String password;

}
