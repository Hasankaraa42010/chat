package com.firatyazilim.DataAccess;

import com.firatyazilim.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship,Integer> {
    List<Friendship> findByUser_Id(int userId);

}
