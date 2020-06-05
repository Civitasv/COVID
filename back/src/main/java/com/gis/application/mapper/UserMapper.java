package com.gis.application.mapper;

import com.gis.application.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User get(String username, String password);

    int add(String username, String password);

    User getByUserName(String username);
}
