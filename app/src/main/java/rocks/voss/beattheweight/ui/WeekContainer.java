package rocks.voss.beattheweight.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import rocks.voss.beattheweight.R;
import rocks.voss.beattheweight.database.Weight;

public class WeekContainer extends LinearLayout {
    final private List<Weight> weights = new ArrayList<>(7);

    @Setter
    private int week = 0;

    public WeekContainer(Context context) {
        this(context, null);
    }

    public WeekContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WeekContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void addWeight(Weight weight) {
        weights.add(weight);

        TextView headline = findViewById(R.id.headline);
        headline.setText("Week: " + week + ": " + calcAvg());

        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        TextView textView = (TextView) inflater.inflate(R.layout.widget_weight, null);
        String text = weight.time.format(DateTimeFormatter.ofPattern("dd.MM.YYYY")) + ": " + weight.weight + " kg";
        textView.setText(text);
        addView(textView);
    }

    private String calcAvg() {
        float avg = 0f;
        for (Weight weight : weights) {
            avg += weight.weight;
        }
        avg /= weights.size();
        avg += 0.05;
        return String.format("%.1f", avg);
    }
}


