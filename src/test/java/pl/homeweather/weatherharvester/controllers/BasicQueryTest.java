package pl.homeweather.weatherharvester.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BasicQueryTest {

    @ParameterizedTest(name = "For given insterval passed to constructor {0}, BasicQuery should return interval of {1}")
    @CsvSource({
            "1, 1",
            "10, 10",
            "0, 1",
            "-10, 1"
    })
    void getInstanceTest(Integer intervalIn, Integer intervalOut) {
        //when
        BasicQuery bq = BasicQuery.getInstance(LocalDateTime.now().minusDays(1), LocalDateTime.now(), intervalIn);

        //then
        assertEquals(intervalOut, bq.getInterval());
    }

    @Test
    void getInstanceTestForNullInterval() {
        //when
        BasicQuery bq = BasicQuery.getInstance(LocalDateTime.now().minusDays(1), LocalDateTime.now(), null);

        //then
        assertEquals(1, bq.getInterval());
    }
}