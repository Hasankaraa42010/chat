package com.firatyazilim.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firatyazilim.model.Friendship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "friendships"})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendships;
}
