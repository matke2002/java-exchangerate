/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matke.exchangerate;

import java.math.BigDecimal;

/**
 *
 * @author ivan
 */
public interface IExchangeRate {
    /**
     * Download exchange rate from remote server
     * @param source Currency source
     * @param destination Currency destination
     * @return Value of exchange rate
     * @throws Exception 
     */
    public BigDecimal getRate(Currency source, Currency destination)  throws Exception;
}
