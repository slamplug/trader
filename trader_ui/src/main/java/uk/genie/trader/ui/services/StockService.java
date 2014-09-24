package uk.genie.trader.ui.services;

import uk.genie.trader.ui.db.StockRepository;
import uk.genie.trader.ui.model.Quote;
import uk.genie.trader.ui.model.StockAlert;

import java.util.List;

public class StockService {

    /**
     * Get Stock price list
     * @return
     */
    public List<Quote> getStockPriceList() { return StockRepository.getQuoteList(); }

    /**
     * Get stock alert list
     * @return
     */
    public List<StockAlert> getStockAlertList() {
        return StockRepository.getStockAlertList();
    }
}
