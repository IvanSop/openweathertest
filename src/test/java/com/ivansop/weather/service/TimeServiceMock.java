package com.ivansop.weather.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * Enables time travel in tests.
 */
@Component
@Profile("test")
public class TimeServiceMock implements TimeService {

    private ZonedDateTime currentTime;

    public void setCurrentTime(ZonedDateTime time) {
        currentTime = time;
    }

    @Override
    public ZonedDateTime getCurrentTime() {
        return currentTime == null ? ZonedDateTime.now() : currentTime;
    }
}
