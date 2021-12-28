package ru.otus.services;

import ru.otus.crm.model.Role;
import ru.otus.crm.service.DBServiceClient;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceClient dbServiceClient;

    public UserAuthServiceImpl(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceClient.findByLogin(login)
            .map(user -> user.getPassword().equals(password) && Role.ADMIN.equals(user.getRole()))
            .orElse(false);
    }

}
