package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class itemCFTest {
	public static void main(String[] args) {
		List<RecommendedItem> recommendations = null;
		try {
			BufferedReader fbr = new BufferedReader(new FileReader(new File("data/placeMap")));
			String line = null;
			HashMap<Long, String> placeMap = new HashMap<Long, String>();
			while ((line = fbr.readLine()) != null) {
				if (placeMap.containsKey(Long.parseLong(line.split(",")[0])) == false) {
					placeMap.put(Long.parseLong(line.split(",")[0]), line.split(",")[1]);
				} else
					continue;
			}

			DataModel model = new FileDataModel(new File("data/FilteredFinal"));

			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);

//			ItemSimilarity similarity = new EuclideanDistanceSimilarity(model);
			Recommender recommender = new GenericItemBasedRecommender(model, similarity);

			LongPrimitiveIterator iter = model.getUserIDs();
			while (iter.hasNext()) {
				long uid = iter.nextLong();
				List<RecommendedItem> list = recommender.recommend(uid, 100);
				System.out.printf("uid:%s", uid);
				for (RecommendedItem ritem : list) {
					System.out.printf("(%s,%f)", placeMap.get(ritem.getItemID()), ritem.getValue());
				}
				System.out.println("\n");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}