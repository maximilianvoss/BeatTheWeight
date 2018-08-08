package rocks.voss.beattheweight.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import org.threeten.bp.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import rocks.voss.androidutils.database.ExportDataSet;

/**
 * Created by voss on 08.04.18.
 */

@Entity(
        primaryKeys = {"time"},
        indices = {
                @Index(value = {"time"})
        }
)
public class Weight implements ExportDataSet {
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

    @Override
    public List<String> getValues() {
        List<String> list = new ArrayList<>(2);
        list.add(time.format(DateTimeFormatter.ofPattern("dd.MM.YYYY")));
        list.add(weight.toString());
        return list;
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
