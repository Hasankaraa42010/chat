package com.firatyazilim.Businiess.abstracts;

import com.firatyazilim.core.DataResult;
import com.firatyazilim.core.Result;
import com.firatyazilim.model.Friendship;

import java.util.List;

public interface FriendshipService {
    Result addFriend(Friendship friendship);
    DataResult<List<Friendship>> getByUserFriensId(int userId);
}
