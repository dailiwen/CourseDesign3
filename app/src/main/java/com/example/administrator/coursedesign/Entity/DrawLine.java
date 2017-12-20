package com.example.administrator.coursedesign.Entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * @author dailiwen
 * @date 2017/12/18 0012 下午 9:29
 */

public class DrawLine extends View {

    private int x;
    private int y;
    private int xGap;
    private int directionJud;

    public DrawLine(Context context, int x, int y, int xGap, int directionJud) {
        super(context);
        this.x = x;
        this.y = y;
        this.xGap = xGap;
        this.directionJud = directionJud;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画线
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth((float) 7.0);
        // 画文本
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
        if (directionJud == 1) {
            canvas.drawLine(x + 30, y + 60, x - xGap + 30, y + 180 - 60, linePaint);
            canvas.drawText("0", x - xGap / 2, y + 95, textPaint);
        } else if (directionJud == 2) {
            canvas.drawLine(x + 30, y + 60, x + xGap + 30, y + 180 - 60, linePaint);
            canvas.drawText("1", x + xGap / 2 + 20, y + 95, textPaint);
        }

    }
}
