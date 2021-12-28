package ru.otus.web.dto;

import ru.otus.crm.model.Client;
import ru.otus.crm.model.Role;


public class ClientDto {

    private Long id;
    private String login;
    private Role role;

    public ClientDto(Long id, String login, Role role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public ClientDto(String login, Role role) {
        this.login = login;
        this.role = role;
    }

    public ClientDto() {
    }

    public Client toClient() {
        return new Client(this.id, this.login, this.role);
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
