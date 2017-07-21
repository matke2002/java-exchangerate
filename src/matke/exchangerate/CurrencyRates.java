/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matke.exchangerate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ivan
 */
public class CurrencyRates implements Comparable<CurrencyRates>{
    private Currency source, destination;
    private Date lastCheched;
    private BigDecimal value;
    private IExchangeRate exchangeRate;
    public CurrencyRates(Currency source, Currency destination, BigDecimal value, IExchangeRate exchangeRate) {
        this.source = source;
        this.destination = destination;
        this.lastCheched = new Date();
        this.value = value;
        this.exchangeRate = exchangeRate;
    }
    /**
     * Returns time when was last syncronization with remote server
     * @return 
     */
    public Date getLastCheckedDate() { return lastCheched; }
    /**
     * Manually sets exchange rate value
     * @param value 
     */
    public void setValue(BigDecimal value) { this.value = value; lastCheched = new Date(); }
    /**
     * Downloads new exchange rate value from server
     * @throws Exception 
     */
    public void refreshValue() throws Exception {
        setValue(exchangeRate.getRate(source, destination));
    }
    /**
     * Return cashed exchange rate value.
     * In case that cashed value is older than 1 hour, it will download new value from remote server
     * @return
     * @throws Exception 
     */
    public BigDecimal getValue() throws Exception { 
        Date now = new Date();
        long diffInMillies = (now.getTime() - this.lastCheched.getTime()) / 60000;
    //    long passedMinutes = getDateDiff(now, this.lastCheched, TimeUnit.MINUTES);
        if (diffInMillies > 60) {
            setValue(exchangeRate.getRate(source, destination));
        }
        return value;
    }

    @Override
    public int compareTo(CurrencyRates o) {
        if (this.source == o.source && this.destination == o.destination && this.exchangeRate == o.exchangeRate) {
            return 0;
        }
        return -1;
    }
}
