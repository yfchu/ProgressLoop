package com.yfchu.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.yfchu.app.progressloop.R;

/**
 * yfchu
 */
public class ProgressLoopView extends View {

    private Context mContext;
    private int roundColor;
    private float roundWidth;
    private int firstColor;
    private int secondColor;
    private int thirdColor;

    private int progress = 240;
    private float mDensity;
    private int formDegree = 150, toDegree = 240;
    private String text = "240天";

    private Paint roundPaint;
    private Paint sweepPaint;
    private Paint textPaint;

    public ProgressLoopView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mDensity = context.getResources().getDisplayMetrics().density;
        roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressLoopView);
        roundColor = a.getColor(R.styleable.ProgressLoopView_roundcolor, ContextCompat.getColor(context, R.color.colorPrimary));
        roundWidth = a.getDimension(R.styleable.ProgressLoopView_roundWidth, dp((float) 1.5));
        firstColor = a.getColor(R.styleable.ProgressLoopView_firstcolor, ContextCompat.getColor(mContext, R.color.colorFirst));
        secondColor = a.getColor(R.styleable.ProgressLoopView_secondcolor, ContextCompat.getColor(mContext, R.color.colorSecond));
        thirdColor = a.getColor(R.styleable.ProgressLoopView_thirdcolor, ContextCompat.getColor(mContext, R.color.colorThird));
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float center = (float) (getWidth() / 2);
        float radius = (float) ((center - roundWidth) / 1.3);
        //画外圈
        OuterCircle(canvas, center, radius);
        //画内圈
        RectF rectF = new RectF(center - radius + dp(20), center - radius + dp(20), center + radius - dp(20), center + radius - dp(20));
        InsideCircle(canvas, center, radius, rectF);//底色环
    }

    private void OuterCircle(Canvas canvas, float center, float radius) {
        float[] pos = new float[2];
        roundPaint.setColor(roundColor);
        roundPaint.setStrokeWidth(roundWidth);
        roundPaint.setStyle(Paint.Style.STROKE);
        roundPaint.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        Path path = new Path();
        path.addArc(rectF, formDegree, toDegree);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getPosTan(pathMeasure.getLength() * 1, pos, null);
        canvas.drawPath(path, roundPaint);
        roundPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(pos[0], pos[1], dp(2), roundPaint);//结尾点
        pathMeasure = new PathMeasure(path, true);
        pathMeasure.getPosTan(pathMeasure.getLength() * 1, pos, null);
        canvas.drawCircle(pos[0], pos[1], dp(2), roundPaint);//起始点
    }

    private void InsideCircle(Canvas canvas, float center, float radius, RectF rectF) {
        sweepPaint.setStrokeWidth(dp(13));
        sweepPaint.setStyle(Paint.Style.STROKE);
        sweepPaint.setStrokeCap(Paint.Cap.ROUND);
        sweepPaint.setShader(null);
        sweepPaint.setColor(ContextCompat.getColor(mContext, R.color.colorBlack));
        canvas.drawArc(rectF, formDegree, toDegree, false, sweepPaint);//底色环
        SweepGradient sweepGradient = new SweepGradient(0, 0, new int[]{firstColor, secondColor, thirdColor}, null);
        sweepPaint.setShader(sweepGradient);
        canvas.drawArc(rectF, formDegree, progress, false, sweepPaint);//彩色环

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(dp(50));
        canvas.drawText(text, center-textPaint.measureText(text)/2, center + dp(50), textPaint);
    }

    /**
     * 刷新进度环
     * */
    public void setProgress(int progress) {
        this.progress = progress;
        text = progress + "天";
        invalidate();
    }

    public int getMax() {
        return toDegree;
    }

    private float dp(float dp) {
        return (dp * mDensity);
    }
}
