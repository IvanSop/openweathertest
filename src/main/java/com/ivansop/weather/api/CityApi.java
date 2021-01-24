package com.ivansop.weather.api;

import com.ivansop.weather.api.dto.CityDto;
import com.ivansop.weather.model.City;
import com.ivansop.weather.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/cities")
@RestController
public class CityApi {

    private final CityService cityService;

    public CityApi(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * List all available cities.
     */
    @GetMapping
    public ResponseEntity<List<CityDto>> list() {
        final List<CityDto> cityList = cityService.list().stream()
                .map(CityDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }

}
