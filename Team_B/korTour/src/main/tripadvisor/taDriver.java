package main.tripadvisor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.jdbc.PostgreSQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.postgresql.ds.PGSimpleDataSource;

import main.mybatis.service.FilteredPlaceService;

public class taDriver {

	final static long myId = 999999;
	final static int NEIGHBORHOOD_NUM = 5;
	final static int RECOMMENDER_NUM = 50;

	public static void main(String[] args) throws TasteException, SQLException {
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setUrl("jdbc:postgresql://35.185.168.167:5432/kortoury");
		ds.setUser("kortoury");
		ds.setPassword("kortoury!@34");
		Connection conn = ds.getConnection();

		JDBCDataModel dm = new PostgreSQLJDBCDataModel(ds, "filteredratings", "userindex", "placeid", "score", null);
		FastByIDMap<PreferenceArray> prfMap = dm.exportWithPrefs();
		GenericUserPreferenceArray myPreference = RandomPlaceEvaluator.getPreferenceArray(myId);
		prfMap.put(myId, myPreference);
		GenericDataModel datamodel = new GenericDataModel(prfMap);

		UserSimilarity usersim = new EuclideanDistanceSimilarity(datamodel);
		NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, usersim, datamodel);
		Recommender r = new GenericUserBasedRecommender(datamodel, neighbor, usersim);

		
		PrintRecommends(r, myId);



	}

	public static void PrintRecommends(Recommender r, long userId) throws TasteException {

		List<RecommendedItem> list = r.recommend(userId, RECOMMENDER_NUM);
		FilteredPlaceService fps = new FilteredPlaceService();
		List<HashMap<String, Object>> places = fps.findAllPlace();

		int i = 1;
		for (RecommendedItem rItem : list) {

			for (HashMap<String, Object> place : places) {
				if (rItem.getItemID() == Long.parseLong(place.get("placeid").toString())) {
					System.out.printf("%d위 : %s\n", i++, place.get("placename").toString());
					break;
				}
				if (i > RECOMMENDER_NUM)
					break;
			}
		}
	}
	
	
}
