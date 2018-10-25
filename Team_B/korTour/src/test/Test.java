package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import main.mybatis.dto.FilteredPlace;
import main.mybatis.dto.FilteredRating;
import main.mybatis.service.FilteredPlaceService;
import main.mybatis.service.FilteredRatingService;

public class Test {
	public static void main(String[] args) throws IOException {
	
// 		FilteredPlace.txt를 db에 적재		
		BufferedReader br = new BufferedReader(new FileReader(new File("data/FilteredPlace")));
		String line = null;
		FilteredPlaceService fps = new FilteredPlaceService();
		int cnt =1;
		while((line = br.readLine()) != null) {
			fps.insertFP(new FilteredPlace(Long.parseLong(line.split(",")[0]),line.split(",")[1]));
			System.out.println(cnt++);
		}
		System.out.println("끝");
	}
}		
//      FilteredFinal.txt를 db에 적재
//		BufferedReader br = new BufferedReader(new FileReader(new File("data/FilteredFinal")));
//		String line = null;
//		FilteredRatingService frs = new FilteredRatingService();
//		int cnt =1;
//		while((line = br.readLine()) != null) {
//			float score = Float.parseFloat(line.split(",")[2]);
//			frs.insertFR(new FilteredRating(Long.parseLong(line.split(",")[0]),Long.parseLong(line.split(",")[1]),score));
//			System.out.println(cnt++);
//		}
//		System.out.println("끝");
//	}
//}		
		
// review수가 일정 개수 이상인 유저의 rating만 filtering 	
//		int config = 30;
//		
//		
//		File f = new File("data/reviewNum");
//		FileReader fr = new FileReader(f);
//		BufferedReader br = new BufferedReader(fr);
//		String line = null;
//		int cnt = 0;
//		Vector<Long> FilteredUser = new Vector<Long>();
//		while((line = br.readLine()) != null) {
//		if(Integer.parseInt(line.split(",")[1])>=config) {
//				FilteredUser.add((Long.parseLong(line.split(",")[0])));
//				cnt++;}
//		else continue;
//			}
//		System.out.println("유저수:"+cnt);
//		br.close();
//		
//		
//		BufferedReader fbr = new BufferedReader(new FileReader(new File("data/FinalRatings")));
//		BufferedWriter fbw = new BufferedWriter(new FileWriter("data/FilteredFinal"));
//		String sline = null;
//		int ccnt = 0;
//		while((sline = fbr.readLine())!= null) {
//			if(FilteredUser.contains(Long.parseLong(sline.split(",")[0]))) {
//				fbw.write(sline+System.lineSeparator());
//				ccnt++;
//			}
//			else 
//				continue;
//			
//		}
//		fbr.close();
//		fbw.close();
//		System.out.println("리뷰개수:"+ccnt);
//	
//		
//		BufferedReader ffbr = new BufferedReader(new FileReader(new File("data/placeMap")));
//		String l = null;
//		HashMap<Long,String> placeMap = new HashMap<Long,String>();
//		while((l = ffbr.readLine())!= null) {
//		if(placeMap.containsKey(Long.parseLong(l.split(",")[0]))==false) {
//			placeMap.put(Long.parseLong(l.split(",")[0]),l.split(",")[1]);
//		}
//		else continue;
//		}
//		ffbr.close();
//		
//		BufferedReader fffbr = new BufferedReader(new FileReader(new File("data/FilteredFinal")));
//		BufferedWriter ffbw = new BufferedWriter(new FileWriter("data/FilteredPlace"));
//		String sl = null;
//		int cccnt = 0;
//		HashMap<String,String> mapWriter = new HashMap<String,String>();
//		while((sl = fffbr.readLine())!= null) {
//			if(mapWriter.containsKey(sl.split(",")[1])==false) {
//				mapWriter.put(sl.split(",")[1],placeMap.get(sl.split(",")[1]));
//				ffbw.write(sl.split(",")[1]+","+placeMap.get(Long.parseLong(sl.split(",")[1]))+System.lineSeparator());
//				cccnt++;
//			}
//			else continue;
//		}
//		fffbr.close();
//		ffbw.close();
//		System.out.println("장소수:"+cccnt+"\n끝");
//	}
//}
		
		
		
		
//		
//		BufferedReader fbr = new BufferedReader(new FileReader(new File("data/FilteredFinal")));
//		String sline = null;
//		Vector<Integer> attrList = new Vector<Integer>();
//		int ccnt = 0;
//		while((sline = fbr.readLine())!= null) {
//		if(attrList.contains(Integer.parseInt(sline.split(",")[1]))==false) {
//			attrList.add(Integer.parseInt(sline.split(",")[1]));
//			ccnt++;
//		    }
//		else continue;
//		}
//		fbr.close();
//		System.out.println(ccnt);
//	}
//	
