package uj.jwzp.ticketmaster.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.entities.User;
import uj.jwzp.ticketmaster.schemas.LoginForm;
import uj.jwzp.ticketmaster.services.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public User register(@RequestBody LoginForm loginForm){
        return userService.register(loginForm.username, loginForm.password);
    }

    @GetMapping("/mytickets")
    public List<Ticket> userTickets(Principal principal){
        return userService.getUserTickets(principal);
    }

    @GetMapping("/wallet")
    public BigDecimal getCash(Principal principal){
        return userService.getWalletStatus(principal);
    }

    @PostMapping("/wallet/add")
    public String addCash(@RequestParam BigDecimal cash, Principal principal){
        userService.addCash(cash, principal);
        return cash + " has been added to your wallet";
    }
}
