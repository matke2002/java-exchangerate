/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matke.exchangerate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author ivan
 */
public class XEModule implements IExchangeRate{

    @Override
    public BigDecimal getRate(Currency source, Currency destination) throws Exception {
        String url = "http://www.xe.com/currencyconverter/convert/?Amount=1&From=EUR&To=RSD";
        url = "http://www.xe.com/currencyconverter/convert/?Amount=1&From=" + source.toString() + "&To=" + destination.toString();
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", "Mozilla/5.0");
        
        HttpResponse response = client.execute(httpGet);
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        
        StringBuffer result = new StringBuffer();
        String line = "";
        String value="";
        
        // <span class='uccResultUnit' data-amount=
        while ((line = rd.readLine()) != null) {
                result.append(line);

                if (line.contains("<span class='uccResultUnit' data-amount=")) {
                    int indx = line.indexOf("<span class='uccResultUnit' data-amount=")+41;
                    line=line.substring(indx);
                //    System.out.println(line);
                    indx = line.indexOf("\"");
                    value = line.substring(0, indx);
                    System.out.println("XE: " + value);
                }
        }
        BigDecimal rezult = new BigDecimal(value);
        return rezult;
    }
    
}
