package com.RestAutomation.testRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"resources/rupeek.feature"}, glue = {"com.RestAutomation.stepdefinition.Rupeek.java"},  plugin = {
        "pretty", "html:target/reports/cucumber"})
public class RupeekTestRunner {

}
