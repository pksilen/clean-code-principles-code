package com.example.salesitemservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userAccountId;

    @NotNull
    private String name;

    @Min(value = 0, message = "Price must be greater than 0")
    @Max(
            value = Integer.MAX_VALUE,
            message = "Price must be <= " + Integer.MAX_VALUE
    )
    private Integer price;

    static SalesItem from(final SalesItemArg salesItemArg) {
        return new ModelMapper()
                .map(salesItemArg, SalesItem.class);
    }

    static SalesItem from(
            final SalesItemArg salesItemArg,
            final Long id
    ) {
        final var salesItem =
                new ModelMapper().map(salesItemArg, SalesItem.class);

        salesItem.setId(id);
        return salesItem;
    }
}
