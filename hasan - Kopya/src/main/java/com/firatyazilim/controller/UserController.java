package com.firatyazilim.controller;

import com.firatyazilim.Businiess.abstracts.UserService;
import com.firatyazilim.core.DataResult;
import com.firatyazilim.core.Result;
import com.firatyazilim.model.Friendship;
import com.firatyazilim.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(name = "api/user/")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("login")
    public DataResult<User> login(@RequestParam String email, @RequestParam String password) {
        return this.userService.login(email,password);
    }
    @PostMapping("signUp")
    public Result signUp(@RequestBody User user) {
        return this.userService.signUp(user);
    }

    @PostMapping("addFriendShip")
    public Result addFriend(@RequestBody Friendship friendship){
        return this.userService.addFriend(friendship);
    }
    @GetMapping("user/allFriend")
    public DataResult<List<Friendship>> getByUserFriensId(@RequestParam int userId){
        return this.userService.getByUserFriensId(userId);
    }
}
