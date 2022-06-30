package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Data
public class Film implements Comparable<Film>{
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private final LocalDate BIRTHDAY_OF_CINEMA = LocalDate.of(1895, 12, 28);

    public Set<Long> getLikes() {
        return likes;
    }

    public void setLikes(Set<Long> likes) {
        this.likes = likes;
    }

    private Set<Long> likes = new TreeSet<>();

    public boolean isValid() {
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

    public Film(@JsonProperty("name") String name,
                @JsonProperty("descriprion") String description,
                @JsonProperty("releaseDate") LocalDate releaseDate,
                @JsonProperty("duration") Integer duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    @Override
    public int compareTo(Film o) {
        int result = 0;
        if(this.likes.size() > 0 && o.likes.size() > 0) {
            result = Long.compare(this.likes.size(), o.likes.size());
        } else if(this.likes.size() == 0 && o.likes.size() > 0) {
            result = -1;
        } else if(this.likes.size() > 0 && o.likes.size() ==0) {
            result = 1;
        } else if(this.likes.size() == 0 && o.likes.size() == 0) {
            result = -1;
        }
        return result;
    }
}
