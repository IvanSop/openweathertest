package com.ivansop.weather.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {

    public static ZonedDateTime toZDT(long seconds) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.of("UTC"));
    }

}
