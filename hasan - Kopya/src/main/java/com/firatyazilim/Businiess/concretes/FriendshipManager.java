package com.firatyazilim.Businiess.concretes;

import com.firatyazilim.Businiess.abstracts.FriendshipService;
import com.firatyazilim.DataAccess.FriendshipRepository;
import com.firatyazilim.core.*;
import com.firatyazilim.model.Friendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipManager implements FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    public FriendshipManager(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Result addFriend(Friendship friendship) {
        if(friendship.getUser().getId()==0 && friendship.getFriend().getId()==0 ){
            return new ErrorResult("Boş olamaz id ler");
        }
        this.friendshipRepository.save(friendship);
        return new SuccessResult("Eklendi");
    }

    @Override
    public DataResult<List<Friendship>> getByUserFriensId(int userId) {
        return new SuccessDataResult<List<Friendship>>(this.friendshipRepository.findByUser_Id(userId),"Arkadaşlar getirildi");

    }
}
