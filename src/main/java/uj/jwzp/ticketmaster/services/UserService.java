package uj.jwzp.ticketmaster.services;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uj.jwzp.ticketmaster.entities.Ticket;
import uj.jwzp.ticketmaster.entities.User;
import uj.jwzp.ticketmaster.exceptions.EntityAlreadyExistsException;
import uj.jwzp.ticketmaster.repositories.TicketRepository;
import uj.jwzp.ticketmaster.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, TicketRepository ticketRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.ticketRepository = ticketRepository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User register(String username, String password) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException("username", username);
        }
        User newUser = new User(username, passwordEncoder.encode(password));
        repository.save(newUser);

        return newUser;
    }

    public BigDecimal getWalletStatus(Principal principal) {
        Optional<User> user = repository.findByUsername(principal.getName());
        if (user.isPresent()) {
            return user.get().getCash();
        }
        return BigDecimal.ZERO;
    }

    public void addCash(BigDecimal cash, Principal principal) {
        Optional<User> user = repository.findByUsername(principal.getName());
        if (user.isPresent()) {
            user.get().setCash(user.get().getCash().add(cash));
            repository.save(user.get());
        }
    }

    public List<Ticket> getUserTickets(Principal principal) {
        Optional<User> user = repository.findByUsername(principal.getName());
        if (user.isPresent()) {
            return ticketRepository.findByReservedBy(user.get());
        }
        return List.of();
    }
}
