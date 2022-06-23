package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Data
public class User {
    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private static long lastUsedId = 1;
    private boolean isValid;
    private Set<Long> friends;

    public void setFriends(Set<Long> friends) {
        this.friends = friends;
    }

    public Set<Long> getFriends() {
        return friends;
    }

    public User(@JsonProperty("id") long id,
                @JsonProperty("email") String email,
                @JsonProperty("login") String login,
                @JsonProperty("name") String name,
                @JsonProperty("birthday") LocalDate birthday) {
        if (id != 0) {
            this.id = id;
        } else this.id = getNextId();
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

    private long getNextId() {
        return lastUsedId++;
    }
}
