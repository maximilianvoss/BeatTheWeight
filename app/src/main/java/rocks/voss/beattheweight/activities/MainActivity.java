package rocks.voss.beattheweight.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import rocks.voss.beattheweight.R;
import rocks.voss.beattheweight.database.Weight;
import rocks.voss.beattheweight.database.WeightDatabase;
import rocks.voss.beattheweight.database.WeightsCache;
import rocks.voss.beattheweight.utils.DatabaseUtil;
import rocks.voss.beattheweight.utils.TimeUtil;
import rocks.voss.beattheweight.utils.ToastUtil;

public class MainActivity extends AppCompatActivity implements WeightEntryDialog.WeightEntryCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseUtil.setWeightDatabase(Room.databaseBuilder(getApplicationContext(), WeightDatabase.class, "Weights").build());

        setContentView(R.layout.activity_main);

        FloatingActionButton addWeightButton = findViewById(R.id.addWeightButton);
        addWeightButton.setOnClickListener((View v) -> {
            WeightEntryDialog dialog = new WeightEntryDialog();
            dialog.show(getFragmentManager(), "newWeight");
        });

        updateWeightList();
    }

    private void updateWeightList() {
        LinearLayout weightList = findViewById(R.id.weightList);
        weightList.removeAllViews();

        WeightsCache.getAll().forEach(weight -> {
            TextView textView = new TextView(this);
            String text = weight.time.format(DateTimeFormatter.ISO_DATE_TIME) + ": " + weight.weight;
            textView.setText(text);
            weightList.addView(textView);
        });
    }

    public void saveNewWeight(float weight) {
        Weight weightObj = new Weight();
        weightObj.time = TimeUtil.getNow();
        weightObj.weight = weight;

        WeightsCache.insertWeight(weightObj);

        ToastUtil.createLongToast(this, "Saved your weight");
        updateWeightList();
    }
}
