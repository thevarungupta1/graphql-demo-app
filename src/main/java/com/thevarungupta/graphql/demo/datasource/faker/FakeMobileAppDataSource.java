package com.thevarungupta.graphql.demo.datasource.faker;

import com.thevarungupta.graphql.demo.codegen.types.Address;
import com.thevarungupta.graphql.demo.codegen.types.Author;
import com.thevarungupta.graphql.demo.codegen.types.MobileApp;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeMobileAppDataSource {
    public static final List<MobileApp> MOBILE_APP_LIST = new ArrayList<>();
    @Autowired
    private Faker faker;

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 20; i++) {
            var author = Author.newBuilder()
                    .name(faker.app().name())
                    .build();
            var mobileApp = MobileApp.newBuilder()
                    .name(faker.app().name())
                    .author(author)
                    .version(faker.app().version())
                    .platform(randomMobileAppPlatform())
                    .build();
            MOBILE_APP_LIST.add(mobileApp);
        }
    }
    private List<String> randomMobileAppPlatform() {
        switch (ThreadLocalRandom.current().nextInt(10) % 3) {
            case 0:
                return List.of("Android");
            case 1:
                return List.of("iOS");
            default:
                return List.of("Android", "iOS");
        }
    }
}
