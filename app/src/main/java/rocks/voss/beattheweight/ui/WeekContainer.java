package rocks.voss.beattheweight.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        headline.setText("Week " + week + ": " + String.format(Locale.US, "%.1f", calcAvg().floatValue()) + " kg");

        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        TextView textView = (TextView) inflater.inflate(R.layout.widget_weight, null);
        String text = weight.time.format(DateTimeFormatter.ofPattern("dd.MM.YYYY")) + ": " + weight.weight + " kg";
        textView.setText(text);
        addView(textView);
    }

    private BigDecimal calcAvg() {
        BigDecimal avg = new BigDecimal(0);

        for (Weight weight : weights) {
            avg = avg.add(weight.weight);
        }
        avg = avg.divide(new BigDecimal(weights.size()), 1, RoundingMode.HALF_UP);
        return avg;
    }
}


