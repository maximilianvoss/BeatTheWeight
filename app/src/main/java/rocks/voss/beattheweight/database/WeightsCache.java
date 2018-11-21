package rocks.voss.beattheweight.database;

import java.util.ArrayList;
import java.util.List;

import rocks.voss.androidutils.utils.DatabaseUtil;

public class WeightsCache {
    private static List<Weight> weights = null;

    public static List<Weight> getAll() {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        if (weights == null) {
            weights = new ArrayList<>();
            databaseUtil.getAll(Weight.class, null, elements -> {
                weights.addAll((List<Weight>) (List<?>) elements);
            });
        }
        return weights;
    }

    public static void insertWeight(Weight weight) {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        weights.add(0, weight);
        databaseUtil.insert(Weight.class, weight);
    }
}
