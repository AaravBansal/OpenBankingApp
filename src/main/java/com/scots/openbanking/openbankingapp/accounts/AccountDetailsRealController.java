//package com.scots.openbanking.openbankingapp.accounts;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//public class AccountDetailsRealController {
//
//    private final AccountService accountService;
//
//    public AccountDetailsRealController(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    @GetMapping("/accounts")
//    public List<Map<String, Object>> getAccounts() throws Exception {
//        return accountService.getAccounts();
//    }
//
//    @GetMapping("/accounts/{accountId}/balances")
//    public List<Map<String, Object>> getBalances(@PathVariable String accountId) throws Exception {
//        return (List<Map<String, Object>>) accountService.getBalances(accountId);
//    }
//}
