package uj.jwzp.ticketmaster.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uj.jwzp.ticketmaster.entities.User;
import uj.jwzp.ticketmaster.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    UserController(UserRepository userRepository){
        this.repository = userRepository;
    }

    @GetMapping()
    public List<User> index() {
        return repository.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password){
        User newUser = new User(username, passwordEncoder.encode(password));
        repository.save(newUser);
        return ResponseEntity.ok().body("Account has been created!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password){
        User user = repository.findByUsernameAndPassword(username, password);
        if (user != null)
            return ResponseEntity.ok().body("You have been logged in!");
        else{
            return ResponseEntity.ok().body("User not found!");
        }
    }

    @GetMapping("/wallet")
    public BigDecimal getCash(){
        return BigDecimal.ZERO;
    }

    @PostMapping("/wallet/add")
    public ResponseEntity<String> addCash(@RequestParam BigDecimal cash){
        return ResponseEntity.ok().body("OK");
    }
}
