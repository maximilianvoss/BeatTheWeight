package rocks.voss.beattheweight.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class HistoryDiagramCanvas extends SurfaceView {
    public HistoryDiagramCanvas(Context context) {
        this(context, null);
    }

    public HistoryDiagramCanvas(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public HistoryDiagramCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setWillNotDraw(false);

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint background = new Paint();
        background.setColor(Color.BLACK);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), background);
    }
}


