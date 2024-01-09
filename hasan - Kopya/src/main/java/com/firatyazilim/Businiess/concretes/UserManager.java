package com.firatyazilim.Businiess.concretes;

import com.firatyazilim.Businiess.abstracts.FriendshipService;
import com.firatyazilim.Businiess.abstracts.UserService;
import com.firatyazilim.DataAccess.FriendshipRepository;
import com.firatyazilim.DataAccess.UserRepository;
import com.firatyazilim.core.*;
import com.firatyazilim.model.Friendship;
import com.firatyazilim.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendshipService friendshipService;
    public UserManager(UserRepository userRepository,FriendshipService friendshipService) {
        this.userRepository = userRepository;
        this.friendshipService=friendshipService;
    }

    @Override
    public DataResult<User> login(String email, String password) {
        if (email==null || password==null) return new ErrorDataResult<User>("Tüm alanlar doldurulmak zorunda..");
        else if(email==null) return new ErrorDataResult<User>("Email zorunlu");
        else if(password==null)return new ErrorDataResult<User>("Şifre zorunlu");
        User user=this.getByUserEmail(email);
        if(user==null) return new ErrorDataResult<User>("Kayıtlı email bulunamadı.");
        else if (!(user.getPassword().equals(password)))
            return new ErrorDataResult<User>("Şifre yanlış...");
        else return new SuccessDataResult<User>(this.getByUserId(user.getId()),"Giriş başarılı");
    }

    @Override
    public User getByUserId(int id) {
        return this.userRepository.getById(id);
    }

    @Override
    public User getByUserEmail(String email) {
        return this.userRepository.getByEmail(email);
    }
    //"sonra bakarsın"
    @Override
    public Result signUp(User user) {
        this.userRepository.save(user);
        return new SuccessResult("Eklendi");
    }
    @Override
    public Result addFriend(Friendship friendship) {
        return this.friendshipService.addFriend(friendship);
    }

    @Override
    public DataResult<List<Friendship>> getByUserFriensId(int userId) {
            return this.friendshipService.getByUserFriensId(userId);
    }


}
