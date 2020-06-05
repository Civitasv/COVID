package com.gis.application.service;

import com.gis.application.model.User;

public interface UserService {
    User get(String username, String password);

    int add(String username,String password);

    User getByUserName(String username);

}
