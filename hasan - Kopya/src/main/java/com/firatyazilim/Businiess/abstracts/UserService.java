package com.firatyazilim.Businiess.abstracts;

import com.firatyazilim.core.Result;
import com.firatyazilim.model.Friendship;
import com.firatyazilim.model.User;
import com.firatyazilim.core.DataResult;

import java.util.List;

public interface UserService {
    DataResult<User> login(String email,String password);
    User getByUserId(int id);
    User getByUserEmail(String email);
    Result signUp(User user);
    Result addFriend(Friendship friendship);
    DataResult<List<Friendship>> getByUserFriensId(int userId);
}
