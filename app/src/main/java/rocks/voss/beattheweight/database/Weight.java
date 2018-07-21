package rocks.voss.beattheweight.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

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
    public float weight;

    public boolean equals(Weight weight) {
        if (weight == null) {
            return false;
        }
        if (this.weight == weight.weight) {
            return true;
        }
        return false;
    }
}
