package com.example.demo;

import org.modelmapper.ModelMapper;

public class SalesItemFactory {
    SalesItem createFrom(final SalesItemArg salesItemArg) {
        return new ModelMapper()
                .map(salesItemArg, SalesItem.class);
    }
    SalesItem createFrom(
            final SalesItemArg salesItemArg,
            final Long id
    ) {
        final var salesItem =
                new ModelMapper().map(salesItemArg, SalesItem.class);

        salesItem.setId(id);
        return salesItem;
    }
}
