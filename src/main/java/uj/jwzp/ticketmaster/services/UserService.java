package uj.jwzp.ticketmaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uj.jwzp.ticketmaster.entities.User;
import uj.jwzp.ticketmaster.exceptions.UserAlreadyExistsException;
import uj.jwzp.ticketmaster.repositories.UserRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public void register(String username, String password){
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()){
            throw new UserAlreadyExistsException("username: " + username);
        }
        User newUser = new User(username, passwordEncoder.encode(password));
        repository.save(newUser);
    }

    public BigDecimal getWalletStatus(Principal principal){
        Optional<User> user = repository.findByUsername(principal.getName());
        if (user.isPresent()){
            return user.get().getCash();
        }
        return BigDecimal.ZERO;
    }

    public void addCash(BigDecimal cash, Principal principal){
        Optional<User> user = repository.findByUsername(principal.getName());
        if (user.isPresent()){
            user.get().setCash(user.get().getCash().add(cash));
            repository.save(user.get());
        }
    }
}
