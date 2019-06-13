package mybooks.integration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class MyBooksITConfig {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()
}
