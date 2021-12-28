package ru.otus.crm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "password")
    private String password;

    public Client() {}

    public Client(String login, Role role, String password) {
        this.id = null;
        this.login = login;
        this.role = role;
        this.password = password;
    }

    public Client(Long id, String login, Role role, String password) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.login, this.role, this.password);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + login + '\'' +
            '}';
    }
}
