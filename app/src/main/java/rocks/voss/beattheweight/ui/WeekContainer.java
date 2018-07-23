package rocks.voss.beattheweight.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import rocks.voss.beattheweight.database.Weight;

public class WeekContainer extends LinearLayout {
    private List<Weight> weights = new ArrayList<>(7);
    private TextView headline;

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
        this.setOrientation(VERTICAL);

        headline = new TextView(context);
        this.addView(headline);
    }

    public void addWeight(Weight weight) {
        weights.add(weight);
        headline.setText("Week: " + week + ": " + calcAvg());
        TextView textView = new TextView(getContext());
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


