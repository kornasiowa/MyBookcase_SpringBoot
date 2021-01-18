package com.kornas.mybookcase.entity;

import com.kornas.mybookcase.validation.EditValidation;
import com.kornas.mybookcase.validation.RegisterValidation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]{1,30}", message = "Podaj poprawne imię",
            groups = {EditValidation.class, RegisterValidation.class})
    private String name;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]{1,30}", message = "Podaj poprawne nazwisko",
            groups = {EditValidation.class, RegisterValidation.class})
    private String surname;

    @NotNull
    @Pattern(regexp = "[a-z0-9\\\\._]{2,30}", message = "Dozwolone litery (a-z), cyfry (0-9) i znaki (._)",
            groups = {EditValidation.class, RegisterValidation.class})
    private String login;

    @NotNull
    @Size(min = 1, message = "Hasło nie może być puste",
            groups = {RegisterValidation.class})
    private String password;

    public User() {
    }

    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
