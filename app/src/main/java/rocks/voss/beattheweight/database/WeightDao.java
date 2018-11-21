package rocks.voss.beattheweight.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

/**
 * Created by voss on 08.04.18.
 */

@Dao
public interface WeightDao {
    @Query("SELECT * FROM weight WHERE datetime(time)>datetime(:time) ORDER BY datetime(time) DESC")
    List<Weight> getAll(OffsetDateTime time);

    @Query("SELECT * FROM weight ORDER BY datetime(time) DESC")
    List<Weight> getAll();

    @Insert
    void insert(Weight weights);
}
