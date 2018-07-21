package rocks.voss.beattheweight.database;

import java.util.ArrayList;
import java.util.List;

import rocks.voss.beattheweight.utils.DatabaseUtil;

public class WeightsCache {
    private static List<Weight> weights = null;

    public static List<Weight> getAll() {
        if (weights == null) {
            weights = new ArrayList<>();
            DatabaseUtil.getAll(elements -> {
                weights.addAll(elements);
            });
        }
        return weights;
    }

    public static void insertWeight(Weight weight) {
        weights.add(0, weight);
        DatabaseUtil.insertAll(weight);
    }
}
