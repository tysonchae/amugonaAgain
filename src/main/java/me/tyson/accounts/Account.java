package me.tyson.accounts;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    private String email;

    @Column(unique = true)
    private String username;

    private String fullName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joined;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

}
