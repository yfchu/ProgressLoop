package com.yfchu.app.progressloop;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nineoldandroids.animation.ValueAnimator;
import com.yfchu.app.view.ProgressLoopView;

public class MainActivity extends AppCompatActivity {
    private ProgressLoopView progressloop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressloop= (ProgressLoopView) findViewById(R.id.progressloop);
    }

    public void onClick(View view){
        ValueAnimator animator= ValueAnimator.ofInt(progressloop.getMax(),100);
        animator.setDuration(2000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progressloop.setProgress((Integer) valueAnimator.getAnimatedValue());
            }
        });
    }
}
