package com.ivansop.weather.api.dto;

import com.ivansop.weather.model.City;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CityDto {

    private long id;
    private String name;

    public CityDto() {
    }

    public CityDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityDto(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
