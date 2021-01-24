package com.ivansop.weather;

import com.ivansop.weather.configuration.WeatherProperties;
import com.ivansop.weather.mock.WeatherDataMock;
import com.ivansop.weather.repository.CityRepository;
import com.ivansop.weather.repository.WeatherDataRepository;
import com.ivansop.weather.service.CityService;
import com.ivansop.weather.service.TimeService;
import com.ivansop.weather.service.TimeServiceMock;
import com.ivansop.weather.service.WeatherDataService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected CityRepository cityRepository;

    @Autowired
    protected WeatherDataRepository weatherDataRepository;

    @Autowired
    protected WeatherDataService weatherDataService;

    @Autowired
    protected CityService cityService;

    @Autowired
    protected TimeServiceMock timeService;

    @Autowired
    protected WeatherProperties weatherProperties;

    @Autowired
    protected WeatherDataMock weatherDataMock;

}
