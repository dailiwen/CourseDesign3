package com.example.administrator.coursedesign.Entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.View;

import com.example.administrator.coursedesign.R;

/**
 * @author dailiwen
 * @date 2017/12/12 0012 下午 9:29
 */

public class DrawView extends View {

    private String character = null;
    private int weight;
    private int x;
    private int y;
    private int xGap;
    private int directionJud;

    public DrawView(Context context, int weight, int x, int y, int xGap, int directionJud) {
        super(context);
        this.weight = weight;
        this.x = x;
        this.y = y;
        this.xGap = xGap;
        this.directionJud = directionJud;
    }

    public DrawView(Context context, String character,int weight, int x, int y, int xGap, int directionJud) {
        super(context);
        this.character = character;
        this.weight = weight;
        this.x = x;
        this.y = y;
        this.xGap = xGap;
        this.directionJud = directionJud;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画一个矩形
        Rect rect = new Rect(x - 60,y - 60, x + 120,y + 60);
        //矩形画笔
        Paint rectPaint = new Paint();
        //空心效果
        rectPaint.setStyle(Paint.Style.STROKE);
        //设置画笔的锯齿效果，true是去除
        rectPaint.setAntiAlias(true);
        //设置线宽
        rectPaint.setStrokeWidth((float) 7.0);
        canvas.drawRect(rect, rectPaint);

        //文字画笔
        Paint textPaint = new Paint();
        textPaint.setTextSize(60);
        textPaint.setAntiAlias(true);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        //为基线到字体上边框的距离,即上图中的top
        float top = fontMetrics.top;
        //为基线到字体下边框的距离,即上图中的bottom
        float bottom = fontMetrics.bottom;
        //基线中间点的y轴计算公式
        int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);
        if (character != null) {
            canvas.drawText(character + " : " + weight, rect.centerX(), baseLineY, textPaint);
        } else {
            canvas.drawText(weight + "", rect.centerX(), baseLineY, textPaint);
        }

    }

}
