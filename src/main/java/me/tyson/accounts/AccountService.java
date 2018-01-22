package me.tyson.accounts;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Created by oolong on 2017-12-24.
 */
@Service
@Transactional
@Slf4j
public class AccountService {

    //private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Account createAccount(AccountDto.Create dto) {

        Account account = modelMapper.map(dto, Account.class);

        //TODO 유효한 username인지 판단
        String username = dto.getUsername();
        if(repository.findByUsername(username) != null){
            //logger.error("user duplicated exception. {}", username);
            log.error("user duplicated exception. {}", username);
            throw new UserDuplicatedException(username);
        }

        //TODO password 해싱

        Date now = new Date();
        account.setJoined(now);
        account.setUpdated(now);

        return repository.save(account);
    }

    public Account updateAccount(Long id, AccountDto.Update updateDto) {
        Account account = getAccount(id);
        account.setPassword(updateDto.getPassword());
        account.setFullName(updateDto.getFullName());
        return repository.save(account);
    }

    public Account getAccount(Long id) {
        Account account = repository.findOne(id);
        if(account == null){
            throw new AccountNotFoundException();
        }
        return account;
    }

    public void delteAccount(Long id) {
        repository.delete(getAccount(id));
    }
}
