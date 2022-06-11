package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private static long lastUsedId = 1;

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

    private long getNextId() {
        return lastUsedId++;
    }
}
