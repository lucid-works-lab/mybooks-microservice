package mybooks.integration

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.jupiter.api.Tag
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest

@Tag("it")
@RunWith(Cucumber::class)
@CucumberOptions(plugin = ["html:build/reports/cucumber/cucumber-html-report",
    "json:build/reports/cucumber/cucumber-json-report.json"],
        features = ["classpath:features"],
        glue = ["mybooks.integration"])
@SpringBootTest
class MyBooksIT
