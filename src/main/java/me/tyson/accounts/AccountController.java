package me.tyson.accounts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tyson on 2017-12-23.
 */
@RestController
public class AccountController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Boot";
    }

}
