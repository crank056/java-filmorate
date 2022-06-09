package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    @NotBlank
    private int id;
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}
