package rocks.voss.beattheweight.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by voss on 08.04.18.
 */

@Entity(
        primaryKeys = {"time"},
        indices = {
                @Index(value = {"time"})
        }
)
public class Weight {
    @NonNull
    public org.threeten.bp.OffsetDateTime time;
    @NonNull
    public BigDecimal weight;

    public boolean equals(Weight weight) {
        if (weight == null) {
            return false;
        }
        if (this.weight.equals(weight.weight)) {
            return true;
        }
        return false;
    }


    public static class TimeComperator implements Comparator<Weight> {
        @Override
        public int compare(Weight o1, Weight o2) {
            return o1.time.compareTo(o2.time);
        }
    }

    public static class WeightComperator implements Comparator<Weight> {
        @Override
        public int compare(Weight o1, Weight o2) {
            return o1.weight.compareTo(o2.weight);
        }
    }
}
