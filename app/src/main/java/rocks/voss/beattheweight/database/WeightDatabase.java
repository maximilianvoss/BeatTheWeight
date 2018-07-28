package rocks.voss.beattheweight.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by voss on 08.04.18.
 */

@Database(entities = {Weight.class}, version = 1)
@TypeConverters({TimeConverter.class, BigDecimalConverter.class})
public abstract class WeightDatabase extends RoomDatabase {
    public abstract WeightDao getDao();
}
