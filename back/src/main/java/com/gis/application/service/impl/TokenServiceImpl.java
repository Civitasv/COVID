package com.gis.application.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gis.application.model.User;
import com.gis.application.service.TokenService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    // 过期时间60分钟
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    @Override
    public String getToken(User user) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = "";
        token = JWT.create().withAudience(user.getUsername()).withExpiresAt(date)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    /**
     * 获取用户从token中
     */
    public String getUserNameFromToken(String token) {
        return JWT.decode(token).getAudience().get(0);
    }



}
