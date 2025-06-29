package com.mygym.crm.backstages;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.mygym.crm.backstages.controllers",
        plugin = {"pretty"}
)
public class CucumberConfig {
}
