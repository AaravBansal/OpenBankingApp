//package com.scots.openbanking.openbankingapp.accounts;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class AccountController {
//
//    private final AccountService accountService;
//
//    public AccountController(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    @GetMapping("/accounts")
//    public List<Map<String, Object>> getAccounts() throws Exception {
//        return accountService.getAccounts();
//    }
//}
