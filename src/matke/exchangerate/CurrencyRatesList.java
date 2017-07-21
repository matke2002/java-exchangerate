/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matke.exchangerate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public class CurrencyRatesList {
    private List<CurrencyRates> rates;
    public CurrencyRatesList() {
        rates = new ArrayList();
    }
    /**
     * Return cashed exchange rate value.
     * In case that cashed value is older than 1 hour, it will download new value from remote server
     * @param exchangeRate remote server identifier
     * @param source Source currency
     * @param destination Destination currency
     * @return Exchange rate value
     * @throws Exception 
     */
    public BigDecimal getRate(IExchangeRate exchangeRate, Currency source, Currency destination) throws Exception {
        BigDecimal rate = null;
        CurrencyRates tmp = new CurrencyRates(source, destination, null, exchangeRate);
        for (CurrencyRates cr:rates) {
            if(cr.compareTo(tmp) == 0) {
                rate = cr.getValue();
                return rate;
            }
        }
        tmp.refreshValue();
        rates.add(tmp);
        rate = tmp.getValue();
        return rate;
    }
    /**
     * Updates all exchange rate values in list from remote servers
     * @throws Exception 
     */
    public void refreshAllRates() throws Exception {
        for(int i=0; i<rates.size(); i++) {
            rates.get(i).refreshValue();
        }
    }
}
