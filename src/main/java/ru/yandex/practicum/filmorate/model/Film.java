package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private long lastUsedId = 1;

    public Film(@JsonProperty("id") long id,
                @JsonProperty("name") String name,
                @JsonProperty("descriprion") String description,
                @JsonProperty("releaseDate") LocalDate releaseDate,
                @JsonProperty("duration") Integer duration) {
        if (id != 0) {
            this.id = id;
        } else this.id = getNextId();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    private long getNextId() {
        return lastUsedId++;
    }

}
