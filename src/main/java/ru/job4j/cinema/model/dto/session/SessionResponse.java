package ru.job4j.cinema.model.dto.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.job4j.cinema.model.SessionState;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionResponse {
    private SessionState sessionState;
}
