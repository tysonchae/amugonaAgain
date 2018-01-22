package me.tyson.accounts;

import me.tyson.commons.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tyson on 2017-12-23.
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Boot";
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create,
                                        BindingResult result){
        if(result.hasErrors()){
            //TODO 에러 응답 본문 추가하기
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Bad Request");
            errorResponse.setCode("bad.request");
            //TODO BindingResult 안에 들어있는 에러 정보 사용하기ds
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Account newAccount = service.createAccount(create);
        return new ResponseEntity(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);
    }

    //전체 업데이트 vs 부분 업데이트
    //전체 업데이트 : PUT
    //- (username:"tyson", password:"pass", fullName:null)

    //부분 업데이트 :PATCH
    //- (username:"tyson")
    //- (password:"pass")
    //- (username:"tyson", password:"pass")
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateAccount(@PathVariable Long id,
                                        @RequestBody @Valid AccountDto.Update updateDto,
                                        BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

//        Account account = repository.findOne(id);
//        if(account == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        Account updatedAccount = service.updateAccount(id, updateDto);
        return new ResponseEntity<>(modelMapper.map(updatedAccount, AccountDto.Response.class),
                HttpStatus.OK);
        // TODO update
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAccount(@PathVariable Long id){
        service.delteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // TODO 예외 처리 네번쨰 방법 (콜백 비스무리한거...)
    @ExceptionHandler(UserDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserDuplicatedException(UserDuplicatedException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getUsername() +"] 중복된 username 입니다");
        errorResponse.setCode("duplicated.username.exception");
        //return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        return errorResponse;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("["+e.getId() + "]에 해당하는 계정이 없습니다.");
        errorResponse.setCode("account.not.found.exception");
        return errorResponse;
    }

    // TODO stream() vs parallelStream()
    // TODO 로깅
    // TODO HATEOAS
    // TODO 뷰 NSPA 1. JSP 2. Thymeleaf
    // TODO 3. angular 4. REACT
    //accounts?page=0&size=20&sort=username&sort=joined,asc
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    //public ResponseEntity getAccounts(Pageable pageable){
    public PageImpl<AccountDto.Response> getAccounts(Pageable pageable){
        Page<Account> page = repository.findAll(pageable);

        List<AccountDto.Response> content = page.getContent().parallelStream()
                .map(account -> modelMapper.map(account, AccountDto.Response.class ))
                .collect(Collectors.toList());

        //PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalElements());
        //return new ResponseEntity<>(result, HttpStatus.OK);
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AccountDto.Response getAccount(@PathVariable Long id){
        Account account = service.getAccount(id);
        return modelMapper.map(account, AccountDto.Response.class);
    }

}
