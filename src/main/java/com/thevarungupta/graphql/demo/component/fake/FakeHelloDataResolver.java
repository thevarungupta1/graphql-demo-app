package com.thevarungupta.graphql.demo.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.thevarungupta.graphql.demo.codegen.types.Hello;
import com.thevarungupta.graphql.demo.datasource.faker.FakeHelloDataSource;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Query should exact match with the schema
 *
 * **/

@DgsComponent
public class FakeHelloDataResolver {

    @DgsQuery
    public List<Hello> allHellos() {
        return FakeHelloDataSource.HELLO_LIST;
    }

    @DgsQuery
    public Hello oneHello() {
        return FakeHelloDataSource.HELLO_LIST.get(
                ThreadLocalRandom.current().nextInt(FakeHelloDataSource.HELLO_LIST.size())
        );
    }
}
