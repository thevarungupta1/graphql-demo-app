package com.thevarungupta.graphql.demo.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.thevarungupta.graphql.demo.codegen.DgsConstants;
import com.thevarungupta.graphql.demo.codegen.types.Book;
import com.thevarungupta.graphql.demo.codegen.types.ReleaseHistory;
import com.thevarungupta.graphql.demo.codegen.types.ReleaseHistoryInput;
import com.thevarungupta.graphql.demo.datasource.faker.FakeBookDataSource;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DgsComponent
public class FakeBookDataResolver {

    @DgsData(parentType = "Query", field = "books")
    public List<Book> bookWithBy(@InputArgument(name = "author") String authorName) {
        if (authorName.isEmpty()) {
            return FakeBookDataSource.BOOK_LIST;
        }

        return FakeBookDataSource.BOOK_LIST
                .stream()
                .filter(book -> book.getAuthor().getName().equals(authorName))
                .collect(Collectors.toList());
    }






    /**
     *
     *
     # Query
     ---------
     query bookByReleased($releaseYear: Int = 2020, $releasePrintedEdition: Boolean){
     booksByReleased(
     releasedInput: {
     year: $releaseYear
     printedEdition: $releasePrintedEdition
     }
     ){
     title
     released{
     year
     }
     author{
     name
     }
     }
     }


     # Variables
     ------------
     {
     "releasePrintedEdition": true,
     "releaseYear": 2021
     }
     *
     *
     * ***/
    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.BooksByReleased
    )
    public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
        var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
        var releasedInput = ReleaseHistoryInput.newBuilder()
                .printedEdition((boolean) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                .year((int)releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                .build();

        return FakeBookDataSource.BOOK_LIST.stream().filter(
                b -> this.matchReleaseHistory(releasedInput, b.getReleased())
        ).collect(Collectors.toList());
    }

    private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory element){
        return input.getPrintedEdition().equals(element.getPrintedEdition()) && input.getYear() == element.getYear();
    }
}
