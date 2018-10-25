package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RatingParser{

public static void main(String[] args)throws ClientProtocolException, IOException {
            
     String url = "https://www.tripadvisor.co.kr/Attraction_Review-g294197-d324888";
     Map<String,Double>userRating =new HashMap<String,Double>();
     boolean isEnd = false;
        
     while(!isEnd){   
         Document doc = Jsoup.connect(url).get();
         Element rvList = doc.select(".listContainer.hide-more-mobile").first();
         
         for(Element rv : rvList.select(".review-container") ){
             String ratingBubble = rv.select(".ui_column.is-9").first().child(0).attr("class").split("_")[3];
             Double rating = Integer.parseInt(ratingBubble)/10.0; 
             String userId = rv.select(".info_text").first().child(0).text();
             userRating.put(userId,rating);
             System.out.println(userId+","+rating);
            }
     
        if (rvList.select(".unified.ui_pagination").first() == null)
             isEnd = true;

        else if (rvList.select(".unified.ui_pagination").first().child(1).attr("href") == "") 
             isEnd = true; 
    
     
        else 
             url = "https://www.tripadvisor.co.kr"+rvList.select(".unified.ui_pagination").first().child(1).attr("href");
       }


 }
}

