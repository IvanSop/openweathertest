package com.ivansop.weather.api;

import com.ivansop.weather.AbstractTest;
import com.ivansop.weather.api.CityApi;
import com.ivansop.weather.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityApiTest extends AbstractTest {

    @BeforeEach
    public void setUp() {
        cityRepository.deleteAll();
    }

    /**
     * Test for {@link CityApi#list()}
     */
    @Test
    public void listAllCitiesTest() throws Exception {
        final String city1Name = "city1";
        final String city2Name = "city2";
        final String city3Name = "city3";

        final City city1 = new City(city1Name);
        final City city2 = new City(city2Name);
        final City city3 = new City(city3Name);

        cityRepository.save(city1);
        cityRepository.save(city2);
        cityRepository.save(city3);

        final MockHttpServletRequestBuilder request = get("/api/cities");

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(city1Name))
                .andExpect(jsonPath("$[1].name").value(city2Name))
                .andExpect(jsonPath("$[2].name").value(city3Name));
    }

}
