package com.mygym.crm.backstages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestDto {
    private int id;

    private String test;

    @JsonCreator
    public TestDto(@JsonProperty("test") String test, @JsonProperty("id") int id) {
        this.test = test;
        this.id = id;
    }
}
