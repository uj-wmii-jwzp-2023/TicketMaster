package uj.jwzp.ticketmaster.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.User;
import uj.jwzp.ticketmaster.services.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public List<User> index() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password){
        userService.register(username, password);
        return ResponseEntity.ok().body("Account has been created!");
    }

    @GetMapping("/me")
    public ResponseEntity<String> userInfo(Principal principal){
        return ResponseEntity.ok().body(principal.getName());
    }

    @GetMapping("/wallet")
    public BigDecimal getCash(Principal principal){
        return userService.getWalletStatus(principal);
    }

    @PostMapping("/wallet/add")
    public ResponseEntity<String> addCash(@RequestParam BigDecimal cash, Principal principal){
        userService.addCash(cash, principal);
        return ResponseEntity.ok().body(cash + " has been added to your wallet");
    }
}
