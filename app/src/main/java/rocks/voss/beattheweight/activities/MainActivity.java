package rocks.voss.beattheweight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.threeten.bp.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import rocks.voss.androidutils.AndroidUtilsConstants;
import rocks.voss.androidutils.activities.ExportGoogleDriveActivity;
import rocks.voss.androidutils.database.ExportData;
import rocks.voss.androidutils.database.ExportDataSet;
import rocks.voss.beattheweight.R;
import rocks.voss.beattheweight.database.Weight;
import rocks.voss.beattheweight.database.WeightsCache;
import rocks.voss.beattheweight.ui.HistoryDiagramCanvas;
import rocks.voss.beattheweight.ui.WeekContainer;
import rocks.voss.beattheweight.utils.DatabaseUtil;
import rocks.voss.beattheweight.utils.TimeUtil;
import rocks.voss.beattheweight.utils.ToastUtil;

public class MainActivity extends AppCompatActivity implements WeightEntryDialog.WeightEntryCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseUtil.openDatabase(getApplicationContext());
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_beattheweight);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("  " + getSupportActionBar().getTitle());

        FloatingActionButton addWeightButton = findViewById(R.id.addWeightButton);
        addWeightButton.setOnClickListener((View v) -> {
            WeightEntryDialog dialog = new WeightEntryDialog();
            dialog.show(getFragmentManager(), "newWeight");
        });

        updateWeightList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.upload:
                intent = new Intent(this, ExportGoogleDriveActivity.class);

                ExportData exportData = new ExportData();

                List<String> headers = new ArrayList<>(2);
                headers.add("Time");
                headers.add("Weight");
                exportData.setHeader(headers);
                exportData.setDataSets((List<ExportDataSet>) (List<?>) WeightsCache.getAll());

                intent.putExtra(AndroidUtilsConstants.EXPORT_GOOGLE_DRIVE_ACTIVITY_EXPORT_DATA, exportData);
                intent.putExtra(AndroidUtilsConstants.EXPORT_GOOGLE_DRIVE_ACTIVITY_EXPORT_FILE_NAME, "weight-%1$s.csv");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateWeightList() {
        LinearLayout weightList = findViewById(R.id.weightList);
        weightList.removeAllViews();

        DateTimeFormatter formatterWeek = DateTimeFormatter.ofPattern("w");

        LayoutInflater inflater = getLayoutInflater();

        WeekContainer weekContainer = null;
        int currentWeek;
        int oldWeek = -1;

        for (Weight weight : WeightsCache.getAll()) {
            currentWeek = Integer.parseInt(weight.time.format(formatterWeek));
            if (currentWeek != oldWeek) {
                oldWeek = currentWeek;
                weekContainer = (WeekContainer) inflater.inflate(R.layout.widget_weekcontainer, null);
                weekContainer.setWeek(currentWeek);
                weightList.addView(weekContainer);
            }
            weekContainer.addWeight(weight);
        }
    }

    public void saveNewWeight(BigDecimal weight) {
        Weight weightObj = new Weight();
        weightObj.time = TimeUtil.getNow();
        weightObj.weight = weight;

        WeightsCache.insertWeight(weightObj);

        ToastUtil.createLongToast(this, "Saved your weight");
        updateWeightList();

        HistoryDiagramCanvas historyDiagramCanvas = findViewById(R.id.historyDiagram);
        historyDiagramCanvas.invalidate();
    }
}
