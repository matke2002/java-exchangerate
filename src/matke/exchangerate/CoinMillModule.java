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
public class CoinMillModule implements IExchangeRate{

    @Override
    public BigDecimal getRate(Currency source, Currency destination) throws Exception {
        String url = "http://coinmill.com/" + source.toString() + "_" + destination.toString() + ".html?" + source.toString() + "=1";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", "Mozilla/5.0");
        
        HttpResponse response = client.execute(httpGet);
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        
        StringBuffer result = new StringBuffer();
        String line = "";
        String value="";
        while ((line = rd.readLine()) != null) {
                result.append(line);

                if (line.contains("<div id=\"currencyBox1\">")) {
                    line = rd.readLine(); // System.out.println(line);
                    int indx = line.indexOf("value=")+7;
                    line=line.substring(indx);
                //    System.out.println(line);
                    indx = line.indexOf("\"");
                    value = line.substring(0, indx);
                    System.out.println("COIN:" + value);
                }
        }
        BigDecimal rezult = new BigDecimal(value);
        return rezult;
    }
    
}
