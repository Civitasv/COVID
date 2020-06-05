package com.gis.application.controller;


import com.gis.application.auth.UserLoginToken;
import com.gis.application.model.User;
import com.gis.application.service.TokenService;
import com.gis.application.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
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
        // 二次加密与服务器检测
        String encryptPwd = DigestUtils.md5DigestAsHex(requestUser.getPassword().getBytes());
        User user = userService.get(username, encryptPwd);

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

    @PostMapping("/checkUsername")
    public String isRepeat(@RequestBody String username) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject(username);
        User user = userService.getByUserName(jsonObject1.getString("username"));
        if (user != null) {
            jsonObject.put("mes", "该用户名已被注册！");
            return jsonObject.toString();
        } else {
            jsonObject.put("mes", "该用户名可用！");
            return jsonObject.toString();
        }
    }

    @PostMapping("/regist")
    public String regist(@RequestBody User requestUser) {
        JSONObject jsonObject = new JSONObject();
        String username = requestUser.getUsername();
        // 二次加密与服务器检测
        String encryptPwd = DigestUtils.md5DigestAsHex(requestUser.getPassword().getBytes());
        userService.add(username, encryptPwd);
        User user = new User(username, encryptPwd);
        String token = tokenService.getToken(user);
        jsonObject.put("token", token);
        jsonObject.put("user", user);
        return jsonObject.toString();
    }

    @UserLoginToken
    @PutMapping("/add")
    public String add() {
        return "add";
    }


}
