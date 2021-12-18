package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
public class Client {

    @Id
    private final Long id;
    private final String login;
    private final Role role;

    public Client(String login, Role role) {
        this(null, login, role);
    }

    @PersistenceConstructor
    public Client(Long id, String login, Role role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Role getRole() {
        return role;
    }
}
