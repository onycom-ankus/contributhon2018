package main.tripadvisor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;

import main.mybatis.service.FilteredPlaceService;

public class RandomPlaceEvaluator {

	public static GenericUserPreferenceArray getPreferenceArray(long userId) {

		FilteredPlaceService fps = new FilteredPlaceService();
		List<HashMap<String, Object>> places = fps.findAllPlace();
		boolean isEnd = false;
		int randomIndex = 0;
		Collections.shuffle(places);

		Scanner scan = new Scanner(System.in);
		List<GenericPreference> prfs = new ArrayList<GenericPreference>();

		System.out
				.println("다음 여행지에 대해 1부터 5까지 평가해주세요.\r방문하지 않았던 여행지의 경우에는 X를 입력하세요.\r평가를 마치고 결과를 보시려면 SUBMIT 을 입력해주세요 ");
		while (!isEnd) {

			System.out.println(places.get(randomIndex).get("placename").toString());
			System.out.print("내 점수 입력: ");
			try {
				float fInput = scan.nextFloat();
				float score = fInput >= 1 && fInput <= 5 ? fInput : -1;

				while (score == -1) {
					System.out.println("1부터 5사이의 값을 입력해주세요.\r");
					System.out.print("내 점수 입력: ");
					fInput = scan.nextFloat();
					score = fInput >= 1 && fInput <= 5 ? fInput : -1;
				}
				
				long placeId = Long.parseLong(places.get(randomIndex).get("placeid").toString());
				prfs.add(new GenericPreference(userId, placeId, score));
				randomIndex++;
			}

			catch (InputMismatchException ime) {
				String sInput = scan.nextLine();
				switch (sInput) {
				case "X":
				case "x":
					randomIndex++;
					continue;
				case "SUBMIT":
					isEnd = true;
					break;
				case "다시":
					randomIndex--;
					continue;
				}
			}
		}

		return new GenericUserPreferenceArray(prfs);
	}
}
