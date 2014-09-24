package uk.genie.trader.collect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {
    private int count;
    private Results results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}
