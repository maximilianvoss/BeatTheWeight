package rocks.voss.beattheweight.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceView;

import org.threeten.bp.OffsetDateTime;

import java.math.BigDecimal;

import rocks.voss.beattheweight.R;
import rocks.voss.beattheweight.database.Weight;
import rocks.voss.beattheweight.database.WeightsCache;
import rocks.voss.beattheweight.utils.TimeUtil;

import static org.threeten.bp.temporal.ChronoUnit.DAYS;

public class HistoryDiagramCanvas extends SurfaceView {

    private float padding;
    private Paint background = new Paint();
    private int daysInHistory;

    public HistoryDiagramCanvas(Context context) {
        this(context, null);
    }

    public HistoryDiagramCanvas(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryDiagramCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("ResourceType")
    public HistoryDiagramCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HistoryDiagramCanvas, defStyleAttr, defStyleRes);
        padding = a.getDimension(R.styleable.HistoryDiagramCanvas_padding, 10f);
        daysInHistory = a.getInt(R.styleable.HistoryDiagramCanvas_days_in_chart, 7);
        background.setColor(Color.parseColor("#FF303030"));
        a.recycle();

        this.setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), background);

        Paint frame = new Paint();
        frame.setColor(Color.BLACK);
        canvas.drawRect(padding, padding, canvas.getWidth() - padding, canvas.getHeight() - padding, frame);

        Paint line = new Paint();
        line.setStyle(Paint.Style.STROKE);
        line.setColor(Color.WHITE);
        line.setStrokeWidth(5f);

        Path path = new Path();
        path.moveTo(padding, padding);

        OffsetDateTime time = TimeUtil.getNow().minusDays(daysInHistory);
        WeightsCache.getAll(weights -> {
            if (weights.size() > 0) {
                weights.sort(new Weight.WeightComperator());

                BigDecimal minimum = weights.get(0).weight;
                BigDecimal maximum = weights.get(weights.size() - 1).weight;

                float fractionKilos = (canvas.getHeight() - 2 * padding) / maximum.subtract(minimum).floatValue();
                float fractionDays = (canvas.getWidth() - 2 * padding) / daysInHistory;

                weights.sort(new Weight.TimeComperator());

                int listStart = 0;
                for (int i = weights.size() - 1; i >= 0; i--) {
                    long daysDiff = DAYS.between(weights.get(i).time, time);
                    if (daysDiff > 0) {
                        listStart = i;
                        break;
                    }
                }
                weights = weights.subList(listStart + 1, weights.size());

                BigDecimal overMinimum = weights.get(0).weight.subtract(minimum);
                path.moveTo(padding, canvas.getHeight() - padding - fractionKilos * overMinimum.floatValue());

                for (Weight weight : weights) {
                    long daysDiff = DAYS.between(time, weight.time) + 1;
                    overMinimum = weight.weight.subtract(minimum);
                    path.lineTo(padding + fractionDays * daysDiff, canvas.getHeight() - padding - fractionKilos * overMinimum.floatValue());
                }
                canvas.drawPath(path, line);
            }
        });
    }
}


