package ru.job4j.cinema.model;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
public class Account {
    private int id;
    @NonNull
    private String phone;
    @NonNull
    private String name;
}
