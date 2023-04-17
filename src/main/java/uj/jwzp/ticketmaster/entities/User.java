package uj.jwzp.ticketmaster.entities;

import jakarta.persistence.*;
import uj.jwzp.ticketmaster.UserRole;

import java.math.BigDecimal;

@Entity
@Table(name="users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal cash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {}

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.cash = BigDecimal.ZERO;
        this.role = UserRole.USER;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public UserRole isStatus() {
        return role;
    }

    public void setStatus(UserRole role) {
        this.role = role;
    }
}
