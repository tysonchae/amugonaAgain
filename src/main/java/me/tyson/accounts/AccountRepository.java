package me.tyson.accounts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tyson on 2017-12-24.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
