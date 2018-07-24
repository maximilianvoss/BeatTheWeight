package rocks.voss.beattheweight.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class WeekDisplay extends EditText {
    public WeekDisplay(Context context) {
        this(context, null);
    }

    public WeekDisplay(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public WeekDisplay(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
