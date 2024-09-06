package com.thevarungupta.graphql.demo.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.thevarungupta.graphql.demo.codegen.types.Hello;
import com.thevarungupta.graphql.demo.codegen.types.HelloInput;
import com.thevarungupta.graphql.demo.datasource.faker.FakeHelloDataSource;

/***
 *
 * mutation addHelloMutation($newHello: HelloInput){
 *   addHello(helloInput: $newHello)
 * }
 *
 *
 * query allHellos {
 *   allHellos{
 *     text
 *     randomNumber
 *   }
 * }
 *
 *
 * {
 *   "newHello": {
 *     "text": "just a new text",
 *     "number" : 9999
 *   }
 * }
 *
 * **/

@DgsComponent
public class FakeHelloMutation {

    @DgsMutation
    public int addHello(@InputArgument(name = "helloInput") HelloInput helloInput) {
        var hello = Hello.newBuilder()
                .text(helloInput.getText())
                .randomNumber(helloInput.getNumber())
                .build();
        FakeHelloDataSource.HELLO_LIST.add(hello);
        return FakeHelloDataSource.HELLO_LIST.size();
    }

}
