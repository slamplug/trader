package uk.genie.trader.service.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.genie.trader.service.model.Quote;

import java.util.HashMap;
import java.util.List;

public class QuoteRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuoteRepository.class);

    private static final HashMap<String, Quote> quotesMap = new HashMap<>();

    public static void upsert(Quote quote) {
        upsertQuoteInMap(quote);
    }

    public static void upsert(List<Quote> quoteList) {
        for (Quote quote: quoteList) {
            upsertQuoteInMap(quote);
        }
    }

    private static synchronized void upsertQuoteInMap(Quote quote) {
        logger.debug("upset quote to repo " + quote);
        if (quotesMap.containsKey(quote.getSymbol())) {
            quote.setPreviousAsk(quotesMap.get(quote.getSymbol()).getAsk());
            quotesMap.remove(quote.getSymbol());
        }
        quotesMap.put(quote.getSymbol(), quote);
    }
}
