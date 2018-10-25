package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserCFTest {

	final static int NEIGHBORHOOD_NUM = 5;
	final static int RECOMMENDER_NUM = 100;

	public static void main(String[] args) throws IOException, TasteException {

		BufferedReader fbr = new BufferedReader(new FileReader(new File("data/placeMap")));
		String line = null;
		HashMap<Long, String> placeMap = new HashMap<Long, String>();
		while ((line = fbr.readLine()) != null) {
			if (placeMap.containsKey(Long.parseLong(line.split(",")[0])) == false) {
				placeMap.put(Long.parseLong(line.split(",")[0]), line.split(",")[1]);
			} else
				continue;
		}

		String file = "data/FilteredFinal";
		DataModel model = new FileDataModel(new File(file));
		UserSimilarity user = new EuclideanDistanceSimilarity(model);
		NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, user, model);
		Recommender r = new GenericUserBasedRecommender(model, neighbor, user);
		LongPrimitiveIterator iter = model.getUserIDs();
		while (iter.hasNext()) {
			long uid = iter.nextLong();
			List<RecommendedItem> list = r.recommend(uid, 100);
			System.out.printf("uid:%s", uid);
			for (RecommendedItem ritem : list) {
				System.out.printf("(%s,%f)", placeMap.get(ritem.getItemID()), ritem.getValue());
			}
			System.out.println("\n");
		}

	}
}
