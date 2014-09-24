package uk.genie.trader.collect.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import uk.genie.trader.collect.model.Quote;
import uk.genie.trader.collect.model.Yahoo;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class YahooQuoteServiceConsumer {

    private static final Logger logger = LoggerFactory.getLogger(YahooQuoteServiceConsumer.class);

    private static final String YAHOO_URL_BASE = "http://query.yahooapis.com/v1/public/yql?";
    private static final String QUERY_START = "q=select * from yahoo.finance.quotes where symbol in (";
    private static final String QUERY_END = "&env=store://datatables.org/alltableswithkeys&format=json";

    public List<Quote> getQuotes(List<String> symbols) throws UnsupportedEncodingException {
        logger.debug("get quotes for symbols '" + Arrays.toString(symbols.toArray()) + "'");
        Yahoo yahoo = new RestTemplate().getForObject(buildUrl(symbols), Yahoo.class);
        return Arrays.asList(yahoo.getQuery().getResults().getQuote());
    }

    private String buildUrl(List<String> symbols) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer(YAHOO_URL_BASE);
        sb.append(buildQuery(symbols)).append(QUERY_END);
        logger.debug("url = '" + sb.toString() + "'");
        return sb.toString();
    }

    private String buildQuery(List<String> symbols) {
        StringBuffer sb = new StringBuffer(QUERY_START);
        boolean firstSymbol = true;
        for (String symbol : symbols) {
            if (!firstSymbol) {
                sb.append(",");
            }
            sb.append("\"").append(symbol).append("\"");
            firstSymbol = false;
        }
        sb.append(")");
        return sb.toString();
    }
}