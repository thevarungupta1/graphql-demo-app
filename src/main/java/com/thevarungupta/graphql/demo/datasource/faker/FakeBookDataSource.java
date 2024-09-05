package com.thevarungupta.graphql.demo.datasource.faker;

import com.thevarungupta.graphql.demo.codegen.types.Address;
import com.thevarungupta.graphql.demo.codegen.types.Author;
import com.thevarungupta.graphql.demo.codegen.types.Book;
import com.thevarungupta.graphql.demo.codegen.types.ReleaseHistory;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeBookDataSource {
    @Autowired
    private Faker faker;
    public static final List<Book> BOOK_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 20; i++) {
            var addresses = new ArrayList<Address>();
            var author = Author.newBuilder().addresses(addresses)
                    .name(faker.book().author())
                    .originCountry(faker.country().name())
                    .build();
            var released = ReleaseHistory.newBuilder()
                    .printedEdition(faker.bool().bool())
                    .releasedCountry(faker.country().name())
                    .year(faker.number().numberBetween(2000, 2024))
                    .build();
            var book = Book.newBuilder()
                    .author(author)
                    .title(faker.book().title())
                    .released(released)
                    .build();
            for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, 3); j++) {
                var address = Address.newBuilder()
                        .city(faker.address().city())
                        .country(faker.country().name())
                        .street(faker.address().streetName())
                        .zipCode(faker.address().zipCode())
                        .build();
                addresses.add(address);
            }
            BOOK_LIST.add(book);
        }
    }
}
