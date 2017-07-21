/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matke.exchangerate;

import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 *
 * @author ivan
 */
public enum Currency {
    RSD("941"), EUR("978"), BAM("977"), USD("840"), 
    CHF("756"), CAD("124"), CZK("203"), HRK("191"),
    MKD("807"), PLN("985"), RUB("643"), NZD("554"),
    AUD("036"), CNY("156"), DKK("208"), JPY("392"),
    KWD("414"), NOK("578"), SEK("752"), GBP("826"),
    HUF("348");
    private String numericCode;
    Currency(String numericCode) {
        this.numericCode = numericCode;
    }
    
    public String getNumericCode() { return this.numericCode; }
    
    public static Currency findByNumericCode(String numericCode) throws Exception{
        for (Currency c: Currency.values()) {
            if (c.getNumericCode().equalsIgnoreCase(numericCode)) {
                return c;
            }
        }
        throw new NotFound("Not found currency by numeric code: " + numericCode, null, null);
    }
    public static Currency convertFromString(String isoCode) throws Exception {
        for (Currency c: Currency.values()) {
            if (c.toString().equalsIgnoreCase(isoCode)) {
                return c;
            }
        }
        throw new NotFound("Not found currency by ISO code: " + isoCode, null, null);
    }
}
