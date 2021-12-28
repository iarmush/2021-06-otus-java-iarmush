package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import ru.otus.messagesystem.client.ResultDataType;

@Table("client")
public class Client implements ResultDataType {

    @Id
    private Long id;
    private String login;
    private Role role;

    public Client() {
    }

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
}
