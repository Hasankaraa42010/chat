package com.firatyazilim.DataAccess;

import com.firatyazilim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User getByEmail(String email);
    User getById(int id);
}
