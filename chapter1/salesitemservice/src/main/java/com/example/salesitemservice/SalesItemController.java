package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SalesItemController.API_ENDPOINT)
@Tag(
        name = "Sales item API",
        description = "Manages sales items"
)
public class SalesItemController {
    public static final String API_ENDPOINT = "/sales-items";

    @Autowired
    private SalesItemService salesItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates new sales item")
    public final SalesItem createSalesItem(
            @RequestBody final SalesItemArg salesItemArg
    ) {
        return salesItemService.createSalesItem(salesItemArg);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets sales items")
    public final Iterable<SalesItem> getSalesItems() {
        return salesItemService.getSalesItems();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets sales item by id")
    public final SalesItem getSalesItemById(
            @PathVariable("id") final Long id
    ) {
        return salesItemService.getSalesItemById(id);
    }

    @GetMapping(params = "userAccountId")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets sales items by user account id")
    public final Iterable<SalesItem> getSalesItemsByUserAccountId(
            @RequestParam("userAccountId") final Long userAccountId
    ) {
        return salesItemService
                .getSalesItemsByUserAccountId(userAccountId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Updates a sales item")
    public final void updateSalesItem(
            @PathVariable final Long id,
            @RequestBody final SalesItemArg salesItemArg
    ) {
        salesItemService.updateSalesItem(id, salesItemArg);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a sales item by id")
    public final void deleteSalesItemById(
            @PathVariable final Long id
    ) {
        salesItemService.deleteSalesItemById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes all sales items")
    public final void deleteSalesItems() {
        salesItemService.deleteSalesItems();
    }
}
