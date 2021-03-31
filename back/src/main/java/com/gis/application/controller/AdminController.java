package com.gis.application.controller;


import com.gis.application.auth.VerifyToken;
import com.gis.application.model.User;
import com.gis.application.model.Virus;
import com.gis.application.service.TokenService;
import com.gis.application.service.UserService;
import com.gis.application.service.VirusService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

    @Autowired
    VirusService virusService;

    @PostMapping("/login")
    public String login(@RequestBody User requestUser) {
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();
        String username = requestUser.getUsername();
        // 二次加密与服务器检测
        String encryptPwd = DigestUtils.md5DigestAsHex(requestUser.getPassword().getBytes());
        User user = userService.get(username, encryptPwd);

        if (null == user) {
            jsonObject.addProperty("message", "登录失败,用户不存在");
            return jsonObject.toString();
        } else {
            String token = tokenService.getToken(user);
            jsonObject.addProperty("token", token);
            jsonObject.addProperty("user", gson.toJson(user));
            return jsonObject.toString();
        }
    }

    @PostMapping("/checkUsername")
    public String isRepeat(@RequestBody String username) {
        JsonObject jsonObject = new JsonObject();
        User user = userService.getByUserName(username);
        if (user != null) {
            jsonObject.addProperty("mes", "该用户名已被注册！");
            return jsonObject.toString();
        } else {
            jsonObject.addProperty("mes", "该用户名可用！");
            return jsonObject.toString();
        }
    }

    @PostMapping("/regist")
    public String regist(@RequestBody User requestUser) {
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();
        String username = requestUser.getUsername();
        // 二次加密与服务器检测
        String encryptPwd = DigestUtils.md5DigestAsHex(requestUser.getPassword().getBytes());
        userService.add(username, encryptPwd);
        User user = new User(username, encryptPwd);
        String token = tokenService.getToken(user);
        jsonObject.addProperty("token", token);
        jsonObject.addProperty("user", gson.toJson(user));
        return jsonObject.toString();
    }


    @VerifyToken
    @PutMapping("/update")
    public int updateVirus(@RequestBody Virus virus) {
        return virusService.updateVirus(virus);
    }

    @VerifyToken
    @DeleteMapping("/delete")
    public int deleteVirus(@RequestParam int id) {
        return virusService.deleteVirus(id);
    }

    @VerifyToken
    @PostMapping("/add")
    public int addVirus(@RequestBody Virus virus) {
        return virusService.insertVirus(virus);
    }
}
