package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Data
public class User {
    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friends = new TreeSet<>();

    public void setFriends(Set<Long> friends) {
        this.friends = friends;
    }

    public Set<Long> getFriends() {
        return friends;
    }

    public User(long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User(@JsonProperty("email") String email,
                @JsonProperty("login") String login,
                @JsonProperty("name") String name,
                @JsonProperty("birthday") LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public boolean isValid() {
        boolean isValid = true;
        if (email.isBlank() || !email.contains("@")) {
            isValid = false;
            log.info("Неверный формат Email");
        }
        if (login.isBlank() || login.contains(" ")) {
            isValid = false;
            log.info("Неверный формат логина");
        }
        if (birthday.isAfter(LocalDate.now())) {
            isValid = false;
            log.info("Неверная дата рождения");
        }
        return isValid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
