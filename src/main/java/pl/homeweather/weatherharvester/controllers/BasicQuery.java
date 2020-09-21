package pl.homeweather.weatherharvester.controllers;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;


@Getter
@ToString
public final class BasicQuery {

    private final LocalDateTime from;
    private final LocalDateTime to;
    private final Integer interval;

    private BasicQuery(LocalDateTime from, LocalDateTime to, Integer interval) {
        this.from = ofNullable(from).orElse(LocalDateTime.now().minusDays(1));
        this.to = ofNullable(to).orElse(LocalDateTime.now());
        if (isNull(interval) || interval < 1) {
            this.interval = 1;
        } else {
            this.interval = interval;
        }
    }

    public static BasicQuery getInstance(LocalDateTime from, LocalDateTime to, Integer interval) {
        return new BasicQuery(from, to, interval);
    }
}
