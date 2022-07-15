package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Data
public class Film implements Comparable<Film> {
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private long id;
    private Set<String> genre = new TreeSet<>();
    private Integer mpaRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Set<String> getGenre() {
        return genre;
    }

    public void setGenre(Set<String> genre) {
        this.genre = genre;
    }

    public Integer getMpaRate() {
        return mpaRate;
    }

    public void setMpaRate(Integer mpaRate) {
        this.mpaRate = mpaRate;
    }

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

    public Film(String name, String description, LocalDate releaseDate, Integer duration, long id, int mpaRate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.id = id;
        this.mpaRate = mpaRate;
    }

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
                @JsonProperty("duration") Integer duration,
                @JsonProperty("mpaRate") Integer mpaRate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpaRate = mpaRate;
    }

    @Override
    public int compareTo(Film o) {
        int result = 0;
        if (this.likes.size() > 0 && o.likes.size() > 0) {
            result = Long.compare(this.likes.size(), o.likes.size());
        } else if (this.likes.size() == 0 && o.likes.size() > 0) {
            result = -1;
        } else if (this.likes.size() > 0 && o.likes.size() == 0) {
            result = 1;
        } else if (this.likes.size() == 0 && o.likes.size() == 0) {
            result = -1;
        }
        return result;
    }
}
