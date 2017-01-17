# ProgressLoop
倒计时彩色进度环

![image](https://github.com/yfchu/ProgressLoop/tree/master/apk2.gif)   
```xml
	//xml
	<com.yfchu.app.view.ProgressLoopView
        android:id="@+id/progressloop"
        android:layout_width="wrap_content"
        android:layout_height="350dp"
        app:firstcolor="@color/colorFirst"
        app:roundWidth="1.5dp"
        app:roundcolor="@color/colorPrimary"
        app:secondcolor="@color/colorSecond"
        app:thirdcolor="@color/colorThird" />
```

```xml
	//attrs
	<declare-styleable name="ProgressLoopView">
        <attr name="roundcolor" format="color" />
        <attr name="roundWidth" format="dimension" />
        <attr name="firstcolor" format="color" />
        <attr name="secondcolor" format="color" />
        <attr name="thirdcolor" format="color" />
    </declare-styleable>
```

```java  
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float center = (float) (getWidth() / 2);
        float radius = (float) ((center - roundWidth) / 1.3);
        //画外圈
        OuterCircle(canvas, center, radius);
        //画内圈
        rectF = new RectF(center - radius + dp(20), center - radius + dp(20), center + radius - dp(20), center + radius - dp(20));
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
```
