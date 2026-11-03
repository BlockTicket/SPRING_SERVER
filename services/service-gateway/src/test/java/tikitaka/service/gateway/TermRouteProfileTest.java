package tikitaka.service.gateway;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import tikitaka.service.gateway.config.TermRouteConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

class TermRouteProfileTest {

    @ParameterizedTest
    @ValueSource(strings = {"default", "prod"})
    void doNotRegisterTermRouteWithoutDevProfile(String profile) {
        new ApplicationContextRunner()
                .withUserConfiguration(TermRouteConfiguration.class)
                .withPropertyValues("spring.profiles.active=" + profile)
                .run(context -> assertThat(context).doesNotHaveBean("termRoute"));
    }
}
