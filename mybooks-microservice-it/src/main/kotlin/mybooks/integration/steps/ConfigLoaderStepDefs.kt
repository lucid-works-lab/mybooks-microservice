package mybooks.integration.steps

import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [RestCallContext::class])
class ConfigLoaderStepDefs : StepDefs
