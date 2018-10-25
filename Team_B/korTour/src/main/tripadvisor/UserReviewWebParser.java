package main.tripadvisor;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.sql.Date;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.mybatis.dto.Review;
import main.mybatis.dto.User;
import main.mybatis.service.PlaceService;
import main.mybatis.service.ReviewService;
import main.mybatis.service.UserService;

public class UserReviewWebParser {
	public static void main(String[] args) throws ClientProtocolException, IOException {

        String url = "https://www.tripadvisor.co.kr/Attraction_Review-g-";
        
        String partAttrCode = "d4798693";
//        String partReviewCode = "r33966786793";
        int partPage = 0;
        
		PlaceService ps = new PlaceService();
		UserService us = new UserService();
		ReviewService rs = new ReviewService();
		int cnt = 0;
		boolean flag1 = false, flag2 = false;
		List<HashMap<String, Object>> attrCodeList = ps.findAllAttrCode();
		for (HashMap<String, Object> map : attrCodeList) {
			String attrCode = String.valueOf(map.get("place_id"));
			int rcnt = 0;
			cnt++;
			////////////////////////////////////////////// start from particular attr
			if (attrCode.equals(partAttrCode)) flag1 = true;
			if (!flag1) continue;
			//////////////////////////////////////////////
//			System.out.println(cnt + ". <" + attrCode + ">");
			Integer totReview = Integer.valueOf(map.get("total_review").toString());
			int n = (int) Math.ceil(Double.valueOf(totReview)/10);
			String[] page = new String[n];
			page[0] = "";
			for (int i=1 ; i<n ; i++) {
				page[i] = "or"+i*10;
			}
			for (int i=(attrCode.equals(partAttrCode)?partPage:0) ; i<n ; i++) {
		        Document doc = Jsoup.connect(url + attrCode + "-Reviews-" + page[i]).get();
				Element reviewDiv = doc.selectFirst("#REVIEWS");
				Elements reviews = reviewDiv.select(".reviewSelector");
				for (Element e: reviews) {
					String UID = e.selectFirst(".member_info div[class^=avatar profile_]").className().split("_")[1];
					Document tempDoc = Jsoup.connect("https://www.tripadvisor.co.kr/MemberProfile-a_uid."+UID).get();

					tempDoc.selectFirst("div.ageSince > p.since").remove();
					String as = tempDoc.selectFirst("div.ageSince").text();
					String as0 = (as.contains("-")) ? as.split("-")[0] : as.split("\\+")[0];
					String as1 = (as.contains("-")) ? as.split("-")[1].replaceAll("[^0-9]", "") : "0";
					Integer toage = Integer.valueOf((as0.equals("") ? "0" : as0));
					Integer fromage = Integer.valueOf(as1.equals("") ? "0" : as1);
					String gender = (as.contains(",")) ? as.substring(as.indexOf(",")+1).trim() : "";
					String residence = tempDoc.selectFirst("div.hometown").text();
					Integer revn = Integer.parseInt(tempDoc.selectFirst("a[name=reviews]").text().replaceAll("[^0-9]", ""));
					Element arevh = tempDoc.selectFirst("a[name=lists]");
					Integer revh = Integer.parseInt((arevh != null) ? arevh.text().replaceAll("[^0-9]", "") : "0");
					User u = new User(UID, toage, fromage, gender, residence, revn, revh);
					String rid = "r" + e.attr("data-reviewid");
					rcnt++;

					////////////////////////////////////////////// start from particular rid
//			        if (rid.equals(partReviewCode)) flag2 = true;
//			        if (!flag2) continue;
					//////////////////////////////////////////////
			        
					System.out.println(cnt + ". <" + attrCode + "> "  + rcnt + ". " + UID + "/" + rid);
					String content = e.selectFirst("span.noQuotes").text();
					Float rate = Float.valueOf(e.selectFirst("span.ui_bubble_rating").className().split("_")[3])/10;
					String ds = e.selectFirst("span.ratingDate").attr("title");
					Date date = new Date(Integer.valueOf(ds.split(" ")[0].replaceAll("[^0-9]", ""))-1900,
							Integer.valueOf(ds.split(" ")[1].replaceAll("[^0-9]", ""))-1,
							Integer.valueOf(ds.split(" ")[2].replaceAll("[^0-9]", "")));
					Review r = new Review(rid, attrCode, UID, content, rate, date);

					us.insertUser(u);
					rs.insertReview(r);
				}
			}
		}
		
	}
}
