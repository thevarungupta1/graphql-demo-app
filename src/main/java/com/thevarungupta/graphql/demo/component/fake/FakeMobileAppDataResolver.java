package com.thevarungupta.graphql.demo.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.thevarungupta.graphql.demo.codegen.DgsConstants;
import com.thevarungupta.graphql.demo.codegen.types.MobileApp;
import com.thevarungupta.graphql.demo.codegen.types.MobileAppFilter;
import com.thevarungupta.graphql.demo.datasource.faker.FakeMobileAppDataSource;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakeMobileAppDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.MobileApps
    )
    public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter", collectionType = MobileAppFilter.class) Optional<MobileAppFilter> filter) {
        if (filter.isEmpty()) {
            return FakeMobileAppDataSource.MOBILE_APP_LIST;
        }
        return FakeMobileAppDataSource.MOBILE_APP_LIST
                .stream()
                .filter(mobileApp -> matchFilter(filter.get(), mobileApp))
                .collect(Collectors.toList());
    }

    private boolean matchFilter(MobileAppFilter mobileAppFilter, MobileApp mobileApp) {
        var isAppMatch = StringUtils.containsAnyIgnoreCase(mobileApp.getName(),
                StringUtils.defaultString(mobileAppFilter.getName(), StringUtils.EMPTY))
                && StringUtils.containsAnyIgnoreCase(mobileApp.getVersion(),
                StringUtils.defaultIfBlank(mobileAppFilter.getVersion(), StringUtils.EMPTY));


        if(!isAppMatch) {
            return false;
        }
        if(StringUtils.isNotBlank(mobileAppFilter.getPlatform()) &&
                !mobileApp.getPlatform().contains(mobileAppFilter.getPlatform())) {
            return false;
        }

        if(mobileAppFilter.getAuthor() != null
        && !StringUtils.containsIgnoreCase(mobileApp.getAuthor().getName(),
                StringUtils.defaultIfBlank(mobileAppFilter.getAuthor().getName(), StringUtils.EMPTY))) {
            return false;
        }
        return true;
    }
}
