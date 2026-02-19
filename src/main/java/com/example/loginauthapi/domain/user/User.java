package com.example.loginauthapi.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usersapp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "nome")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "senha")
    private String password;
    @Column(name = "ds_app")
    private String descriptionApp;
}
