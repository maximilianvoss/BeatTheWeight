package rocks.voss.beattheweight.database;

import android.arch.persistence.room.TypeConverter;

public class DecimalConverter {
    @TypeConverter
    public static DecimalType toDecimalType(String value) {
        return DecimalType.createByString(value);
    }

    @TypeConverter
    public static String fromDecimalType(DecimalType date) {
        return date.toString();
    }
}
