package ru.job4j.cinema.model;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Account {
    private int id;
    @NonNull
    private String phone;
    @NonNull
    private String name;
}
