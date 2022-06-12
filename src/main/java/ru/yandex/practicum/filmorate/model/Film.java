package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Data
public class Film {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private long lastUsedId = 1;
    private final LocalDate BIRTHDAY_OF_CINEMA = LocalDate.of(1895, 12, 28);
    private boolean isValid;

    public boolean isValid() {
        return validate();
    }

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

    private boolean validate() {
        boolean isValid = true;
        if (name == null || name.isBlank()) {
            isValid = false;
            log.info("Имя не существует или пустое");
        }
        if (description.length() > 200) {
            isValid = false;
            log.info("Размер описания превышает 200 символов");
        }
        if (releaseDate.isBefore(BIRTHDAY_OF_CINEMA)) {
            isValid = false;
            log.info("Дата релиза раньше даты рождения первого фильма в истории!");
        }
        if (duration <= 0) {
            isValid = false;
            log.info("Продолжительность равна или меньше нуля");
        }
        return isValid;
    }
}
