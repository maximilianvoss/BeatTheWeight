package rocks.voss.beattheweight.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import rocks.voss.androidutils.utils.DatabaseUtil;

/**
 * Created by voss on 08.04.18.
 */

@Database(entities = {Weight.class}, version = 1)
@TypeConverters({TimeConverter.class, BigDecimalConverter.class})
public abstract class WeightDatabase extends RoomDatabase implements DatabaseUtil.Database {
    public abstract WeightDao getWeightDao();

    public <DaoObject> DaoObject getDao(Class daoElement) {
        if (daoElement.isAssignableFrom(Weight.class)) {
            return (DaoObject) getWeightDao();
        }
        return null;
    }
}
