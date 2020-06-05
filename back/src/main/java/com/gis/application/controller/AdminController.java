package com.gis.application.controller;


import com.gis.application.auth.UserLoginToken;
import com.gis.application.model.User;
import com.gis.application.service.TokenService;
import com.gis.application.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

   @PostMapping("/login")
    public String login(@RequestBody User requestUser) {
        JSONObject jsonObject = new JSONObject();
        String username = requestUser.getUsername();
        User user = userService.get(username, requestUser.getPassword());

        if (null == user) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject.toString();
        } else {
            String token = tokenService.getToken(user);
            jsonObject.put("token", token);
            jsonObject.put("user", user);
            return jsonObject.toString();
        }
    }
    @UserLoginToken
    @PutMapping("/add")
    public String add(){
        return "add";
    }



}
