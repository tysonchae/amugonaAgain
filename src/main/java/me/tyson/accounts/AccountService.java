package me.tyson.accounts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;


/**
 * Created by oolong on 2017-12-24.
 */
@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Account createAccount(AccountDto.Create dto) {

        Account account = modelMapper.map(dto, Account.class);

        //TODO 유효한 username인지 판단
        String username = dto.getUsername();
        if(repository.findByUsername(username) != null){
            throw new UserDuplicatedException(username);
        }

        //TODO password 해싱

        Date now = new Date();
        account.setJoined(now);
        account.setUpdated(now);

        return repository.save(account);
    }
}