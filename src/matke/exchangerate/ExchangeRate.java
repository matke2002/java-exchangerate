/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matke.exchangerate;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author ivan
 */
public class ExchangeRate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        CurrencyRatesList crl = new CurrencyRatesList();
        IExchangeRate nbs = new NBSModule();
        IExchangeRate coin = new CoinMillModule();
        IExchangeRate xe = new XEModule();
        
        
        BigDecimal rate = null;
        
        try {
            rate = crl.getRate(xe, Currency.EUR, Currency.NOK);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(nbs, Currency.EUR, Currency.RSD);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(xe, Currency.EUR, Currency.RSD);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(coin, Currency.EUR, Currency.RSD);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(nbs, Currency.USD, Currency.RSD);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(coin, Currency.EUR, Currency.NOK);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(xe, Currency.EUR, Currency.NOK);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(nbs, Currency.EUR, Currency.NOK);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(xe, Currency.EUR, Currency.JPY);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(nbs, Currency.EUR, Currency.RSD);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rate = crl.getRate(xe, Currency.EUR, Currency.NOK);
            System.out.println(rate.toString());
        } catch (Exception ex) {
            Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
/*        IExchangeRate cmd = new NBSModule();
        cmd = new CoinMillModule();
        cmd = new XEModule();
        try {
            BigDecimal val =  cmd.getRate(Currency.JPY, Currency.RSD);
            System.out.println("Val: " + val.toString());
            
            val =  cmd.getRate(Currency.JPY, Currency.RSD);
            System.out.println("Val: " + val.toString());
            
            val =  cmd.getRate(Currency.EUR, Currency.RSD);
            System.out.println("Val: " + val.toString());
            val =  cmd.getRate(Currency.AUD, Currency.RSD);
            System.out.println("Val: " + val.toString());
            val =  cmd.getRate(Currency.KWD, Currency.RSD);
            System.out.println("Val: " + val.toString());
            val =  cmd.getRate(Currency.USD, Currency.RSD);
            System.out.println("Val: " + val.toString());
            val =  cmd.getRate(Currency.CZK, Currency.RSD);
            System.out.println("Val: " + val.toString());
            val =  cmd.getRate(Currency.EUR, Currency.SEK);
            System.out.println("Val: " + val.toString());
            //Vezbanka v = new Vezbanka();
            //v.posalji();
            */
            /*

            HttpClient client = new DefaultHttpClient();
            String urlToRead = "http://www.nbs.rs/export/sites/default/internet/latinica/scripts/ondate.html";
           

            
            
            
            
            
            urlToRead = "http://www.nbs.rs/kursnaListaModul/naZeljeniDan.faces;jsessionid=C8C38DAEF22C484DA340F93524545581";
            
            
            
		HttpPost post = new HttpPost(urlToRead);

		// add header
		post.setHeader("User-Agent", "Mozilla/5.0");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("index", "index"));
		urlParameters.add(new BasicNameValuePair("index:brKursneListe", ""));
		urlParameters.add(new BasicNameValuePair("index:buttonShow", "Prikaži"));
		urlParameters.add(new BasicNameValuePair("index:inputCalendar1", "21.07.2017."));
		urlParameters.add(new BasicNameValuePair("index:prikaz", "1"));
                urlParameters.add(new BasicNameValuePair("index:vrsta", "3"));
		urlParameters.add(new BasicNameValuePair("index:year", "2017"));
                urlParameters.add(new BasicNameValuePair("javax.faces.ViewState", "7700316217472126612:-7367040890397675966"));
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + urlToRead);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
                                    response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());
            
          */
            
            
            
            
   //     } catch (Exception ex) {
   //         Logger.getLogger(ExchangeRate.class.getName()).log(Level.SEVERE, null, ex);
    //    }
    }
    
}
