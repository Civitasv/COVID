package com.gis.application.service.impl;

import com.gis.application.mapper.UserMapper;
import com.gis.application.model.User;
import com.gis.application.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    // 此种方式注入的sqlSession实质上为sqlSessionTemplate，不需要手动关闭
    private final SqlSession sqlSession;

    public UserServiceImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    @Override
    public User get(String username, String password) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.get(username,password);
    }

    @Override
    public int add(String username, String password) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.add(username,password);
    }

    @Override
    public User getByUserName(String username) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.getByUserName(username);
    }
}
