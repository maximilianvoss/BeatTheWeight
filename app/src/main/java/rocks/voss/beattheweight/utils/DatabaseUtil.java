package rocks.voss.beattheweight.utils;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import rocks.voss.beattheweight.Constants;
import rocks.voss.beattheweight.database.Weight;
import rocks.voss.beattheweight.database.WeightDao;
import rocks.voss.beattheweight.database.WeightDatabase;

public class DatabaseUtil {
    @Setter
    @Getter
    private static WeightDatabase weightDatabase;

    public interface GetAllCallback {
        void onResultReady(List<Weight> weights);
    }

    public static void openDatabase(Context context) {
        setWeightDatabase(Room.databaseBuilder(context, WeightDatabase.class, Constants.DATABASE_NAME).build());
    }

    public static WeightDao getWeightDao() {
        if (weightDatabase == null) {
            return null;
        }
        return weightDatabase.getDao();
    }

    public static void insertAll(Weight... weights) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                getWeightDao().insertAll(weights);
            }
        };
        thread.start();
    }

    public static void getAll(GetAllCallback callback) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                List<Weight> result = getWeightDao().getAll();
                callback.onResultReady(result);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("rocks.voss.beattheweight.utils.DatabaseUtil", "InterruptedException", e);
        }
    }
}
