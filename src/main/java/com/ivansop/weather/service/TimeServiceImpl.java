package com.ivansop.weather.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Profile("production")
public class TimeServiceImpl implements TimeService {

    @Override
    public ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }

}
