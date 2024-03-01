package com.example.salesitemservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesItemServiceImpl implements SalesItemService {
    private static final String SALES_ITEM = "Sales item";
    private final SalesItemRepository salesItemRepository;

    @Autowired
    public SalesItemServiceImpl(final SalesItemRepository salesItemRepository) {
        this.salesItemRepository = salesItemRepository;
    }

    @Override
    public final SalesItem createSalesItem(
            final SalesItemArg salesItemArg
    ) {
        final var salesItem = SalesItem.from(salesItemArg);
        return salesItemRepository.save(salesItem);
    }

    @Override
    public final SalesItem getSalesItemById(final Long id) {
        return salesItemRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundError(SALES_ITEM, id));
    }

    @Override
    public final Iterable<SalesItem> getSalesItemsByUserAccountId(
            final Long userAccountId
    ) {
        return salesItemRepository
                .findByUserAccountId(userAccountId);
    }

    @Override
    public final Iterable<SalesItem> getSalesItems() {
        return salesItemRepository.findAll();
    }

    @Override
    public final void updateSalesItem(
            final Long id,
            final SalesItemArg salesItemArg
    ) {
        if (salesItemRepository.existsById(id)) {
            final var salesItem =
                    SalesItem.from(salesItemArg, id);

            salesItemRepository.save(salesItem);
        } else {
            throw new EntityNotFoundError(SALES_ITEM, id);
        }
    }

    @Override
    public final void deleteSalesItemById(final Long id) {
        if (salesItemRepository.existsById(id)) {
            salesItemRepository.deleteById(id);
        }
    }

    @Override
    public final void deleteSalesItems() {
        salesItemRepository.deleteAll();
    }
}
