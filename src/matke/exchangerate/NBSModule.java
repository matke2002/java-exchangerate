/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matke.exchangerate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author ivan
 */
public class NBSModule implements IExchangeRate{
    private Date lastChecked;
    private HashMap<Currency,BigDecimal> hm;
    public NBSModule() {
        lastChecked = new Date(0);
        hm = new HashMap<Currency,BigDecimal>();
    }

    @Override
    public BigDecimal getRate(Currency source, Currency destination) throws Exception{
        if (!destination.equals(Currency.RSD)) {
            throw new DestinationCurrencyError("ONLY RSD is supported in NBS module as destination");
        }
        BigDecimal value = null;
        StringBuffer readDataFromSite = null;
        if (hm.isEmpty()) {
            readDataFromSite = readDataFromSite();
        }
        value = parseSiteCsv(readDataFromSite, source);
        System.out.println("NBS: " + value.toString());
        
        return value;
    }
    private BigDecimal parseSiteCsv(StringBuffer data, Currency source) {
        BigDecimal result = null;
        
        Date now = new Date();
        long diffInMillies = (now.getTime() - lastChecked.getTime()) / 60000;
    //    long passedMinutes = getDateDiff(now, this.lastCheched, TimeUnit.MINUTES);
        boolean rebuildList = false;
        if (diffInMillies > 60) {
            rebuildList = true;
            lastChecked = new Date();
            hm.clear();
        }

        if (rebuildList) {
            String [] lines = data.toString().split("\n");
        
            for (String line:lines) {
                String [] elements = line.split(",");
                try {
                //if (source.toString().equalsIgnoreCase(elements[4])) {
                    BigDecimal sourceValue = new BigDecimal(elements[5]);
                    BigDecimal destinationValue = new BigDecimal(elements[6]);
                    BigDecimal div = destinationValue.divide(sourceValue);
                    hm.put(Currency.convertFromString(elements[4]), div);
                //}
                } catch (Exception ex) {  }
            }
        }
        result = hm.get(source);
        return result;
    }
    private StringBuffer readDataFromSite() throws Exception {
        String url = "http://www.nbs.rs/export/sites/default/internet/latinica/scripts/ondate.html";
        url = "http://www.nbs.rs/kursnaListaModul/naZeljeniDan.faces?lang=lat";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", "Mozilla/5.0");
        
        HttpResponse response = client.execute(httpGet);
        
        BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

        String javax_faces_ViewState = "";
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
                        
                        if (line.contains("javax.faces.ViewState")) {
                    //        System.out.println(line);
                            int indx = line.indexOf("value=")+7;
                            line=line.substring(indx);
                    //        System.out.println(line);
                            indx = line.indexOf("\"");
                            javax_faces_ViewState = line.substring(0, indx);
                    //        System.out.println(javax_faces_ViewState);
                        }
		}
                    
		
        
        
        
     //   response.getHeaders("Cookie");
        org.apache.http.Header[] headers = response.getHeaders("Set-Cookie"); //response.getAllHeaders(); // 
    //    System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        
    //    System.out.println("jsession: " + getSessionId(headers[0]));
        HeaderElement[] elements = headers[0].getElements();
        String jsessionID=elements[0].getValue();
        // POST
        url = "http://www.nbs.rs/kursnaListaModul/naZeljeniDan.faces;jsessionid=" + jsessionID;
        url = "http://www.nbs.rs/kursnaListaModul/naZeljeniDan.faces";
        HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", "Mozilla/5.0");
                post.setHeader("Cookie", "JSESSIONID=" + jsessionID);
             //   post.setHeader("Cookie", "JSESSIONID=3C90CABD4C72CBB63FC222A6BB8B6CAD");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("index", "index"));
		urlParameters.add(new BasicNameValuePair("index:brKursneListe", ""));
		urlParameters.add(new BasicNameValuePair("index:buttonShow", "Prikaži"));
                Date now = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy.");
                String dateS = format1.format(now);
                urlParameters.add(new BasicNameValuePair("index:inputCalendar1", dateS));
		urlParameters.add(new BasicNameValuePair("index:prikaz", "1"));
                urlParameters.add(new BasicNameValuePair("index:vrsta", "3"));
                format1 = new SimpleDateFormat("yyyy");
                dateS = format1.format(now);
		urlParameters.add(new BasicNameValuePair("index:year", dateS));
                urlParameters.add(new BasicNameValuePair("javax.faces.ViewState", javax_faces_ViewState));
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		response = client.execute(post);
	//	System.out.println("\nSending 'POST' request to URL : " + url);
	//	System.out.println("Post parameters : " + post.getEntity());
	//	System.out.println("Response Code : " +
         //                           response.getStatusLine().getStatusCode());

		rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                result = new StringBuffer();
		line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line).append("\n");
		}

		//System.out.println(result.toString());
                return result;
    }
    
    private class DestinationCurrencyError extends Exception {
        public DestinationCurrencyError(String message) { super(message); }
    }
    
}
