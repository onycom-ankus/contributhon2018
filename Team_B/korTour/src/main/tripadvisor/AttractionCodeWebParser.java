package main.tripadvisor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

import main.mybatis.dto.Place;
import main.mybatis.service.PlaceService;
import main.mybatis.service.ReviewService;
import main.mybatis.service.UserService;

public class AttractionCodeWebParser{

    public static void main(String[] args) throws ClientProtocolException, IOException {
        String url = "https://www.tripadvisor.co.kr/Attractions-g294196";
        // oa0 oa20 oa70 oa120
        // page 1
        Document doc = Jsoup.connect(url).get();
        HashMap<String, String> geoCode = new HashMap<String, String>();
        Element navlist = doc.select(".navigation_list").last();
        for (Element a: navlist.select("a.taLnk")) {
            String href = a.attr("href");
            String g = href.split("-")[1];
            String name = a.text().split(" ")[0];
            name = name.substring(0,name.length()-1);
            if (name.length() == 0) continue;
            geoCode.put(g, name);
        }
        /*String addUrl = doc.select(".ap_navigator > a").last().attr("href");*/
        String[] oaOff={"20","70","120"};
        for(int i=0; i<3 ;i++) {
            doc = Jsoup.connect(url +"-Activities-oa"+oaOff[i]).get();
            navlist = doc.select(".geoList").first();
            for (Element li : navlist.select("li>a")){
                String href = li.attr("href");
                String g = href.split("-")[1];
                String name = li.text().split(" ")[0];
                geoCode.put(g, name);
            }
        }

        //geoCode: g~~~,�����̸� �� ����� hashmap
        
        boolean doOver5 = true; // 5�� �ʰ��� �߸� �� true
        boolean flag = false;
        
        System.out.println(((doOver5)?"���� 5�� �ʰ��� ��Ҹ� ũ�Ѹ��մϴ�.":""));
        int cnt = 0;
        String output = "data/ta/AttrList" + ((doOver5)?"Over5":"") + ".txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
        
        PlaceService ps = new PlaceService();
       
        //AttrList ����ϴ� �ڵ� ����

        for(Map.Entry<String, String> map : geoCode.entrySet()){
            String Code = map.getKey();
            String Geo  = map.getValue();
            System.out.println("<" + Geo + ">");
            
//             start from particular geo
//            if (Code.equals("g612376")) flag = true;
//            if (!flag) continue;

            String murl = "https://www.tripadvisor.co.kr/Attractions-" + Code;
            String placeurl = "https://www.tripadvisor.co.kr/Attraction_Review-" + Code;


            Document mdoc=Jsoup.connect(murl).get();
            
            boolean broadened = (mdoc.select("div#geobroaden_opt_out").first() != null);
            // ��� ���� ��� �ֺ���ұ��� ���� ������ ��� true ��ȯ
            
            HashMap<String,String> AttrMap = new HashMap<String,String>();
            HashMap<String,String> TagMap = new HashMap<String,String>();
            Element filter0 = mdoc.select(".filter_list_0").first();

            for(Element TagLink : filter0.select("a.taLnk")){
                String TagCode = TagLink.attr("href").split("-")[3];
                String TagName = TagLink.text();
                TagName=TagName.substring(0,TagName.lastIndexOf("(")).trim();
                TagMap.put(TagCode,TagName);
            }

            for(String TagCode : TagMap.keySet()){
            	String TagName = TagMap.get(TagCode);
            	//Ž������ �±� ǥ�� 
                String FilteredUrl = murl + "-" + TagCode;
                boolean isEnd = false;
                System.out.println(TagCode + "(" + TagName + ")");
                while(!isEnd) {
                    Document FilteredDoc = Jsoup.connect(FilteredUrl).get();
                    Element fl = FilteredDoc.select("#FILTERED_LIST").first();

                    for (Element li : fl.select(".listing_info"))  {
                    	// ���䰳���� Ȯ���ϰ� 5�� �Ʒ��δ� �ɷ��ִ� �κ� 
                    	if (doOver5) {
                        	Element test = li.select(".more").first();
                        	if (test == null) continue;

                        	String review = test.text().split(" ")[0];
                        	review = review.substring(0,review.length()-2).replace(",","");
                        	int reviewNum = Integer.parseInt(review);

                        	if (reviewNum <= 5) continue;
                    	}

                        String AttrCode = li.selectFirst(".listing_title > a").attr("href").split("-")[2];
                        String AttrName = li.selectFirst(".listing_title > a").text();
                        String GeoContain = li.select(".listing_rating > .popRanking").last().text();
                        if (broadened && !GeoContain.contains(Geo)) continue; // �ش� ������ �ƴϸ� ����X
                                               
                        Document placeDoc = Jsoup.connect(placeurl + "-" + AttrCode).get();
                        
                        Element addDiv = placeDoc.selectFirst(".detail_section.address");
                        String address = (addDiv != null) ? addDiv.text() : "";
                        Element phoneDiv = placeDoc.selectFirst(".detail_section.phone");
                        String phone = (phoneDiv != null) ? phoneDiv.text() : "";
                        Float rateAvg = Float.valueOf(placeDoc.selectFirst(".overallRating").text());
//                        Integer totReview = Integer.valueOf(placeDoc.selectFirst(".reviews_header_count").text().replace("(", "").replace(")","").replace(",", ""));
                        Element revCntSpan = placeDoc.selectFirst("label[for$=ko] > span.count");
                        Integer totReview = (revCntSpan != null) ? Integer.valueOf(revCntSpan.text().replace("(", "").replace(")","").replace(",", "")) : 0; // korean reviews
                        if (totReview <= 5) continue; // skip if korean review <= 5
                        String imgURL = "";
                        Place p = new Place(AttrCode, AttrName, address, phone, rateAvg, totReview, TagCode, imgURL);
                        ps.insertPlace(p);
                        cnt++;
                        System.out.println(Code + "," + Geo + "," + AttrCode + "," + AttrName);
                        bw.write(Code + "," + Geo + "," + AttrCode + "," + AttrName + "," + TagCode + "," + TagName + System.lineSeparator());
                    }        

                    if (FilteredDoc.select(".pagination").first() == null)
                        isEnd = true;

                    else if (FilteredDoc.select(".pagination > div").first().child(1).tag().toString() != "a") 
                        isEnd = true; 

                    else 
                         FilteredUrl = "https://www.tripadvisor.co.kr"+FilteredDoc.select(".pagination > div").first().child(1).attr("href");
                }
            }
        // �� ���� �Ľ� ��
        }
        //��� ���� �Ľ� ��
        System.out.println("�� ��� ��:" + cnt);
        bw.close();
    }

}