package com.luoxiang.winprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * projectName: 	    WinProgress
 * packageName:	        com.luoxiang.winprogress
 * className:	        Win10Progress
 * author:	            Luoxiang
 * time:	            2016/9/20	12:07
 * desc:	            模仿win10系统的进度圈,并非原创,只是自己保存起来,如果侵权,请告知,会立即删除
 *                          并非原创,原创作者地址:http://blog.csdn.net/zhangml0522/article/details/52556418
 *                          原创作者博客:http://my.csdn.net/zhangml0522
 */
public class Win10Progress extends View {
    private Paint       mPaint;
    private Path        mPath;
    private PathMeasure mPathMeasure;
    private int         mWidth,mHeight;
    private ValueAnimator valueAnimator;
    //用这个来接受ValueAnimator的返回值，代表整个动画的进度
    private float         t;

    public Win10Progress(Context context) {
        this(context , null);
    }

    public Win10Progress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        valueAnimator.start();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.RED);
        //设置画笔为园笔
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //抗锯齿
        mPaint.setAntiAlias(true);

        mPath = new Path();
        RectF rect = new RectF(-150, -150, 150, 150);
        mPath.addArc(rect,-90,359.9f);

        mPathMeasure = new PathMeasure(mPath,false);

        valueAnimator = ValueAnimator.ofFloat(0f,1f).setDuration(3000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                t = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        Path dst = new Path();
        if(t>=0.95){
            canvas.drawPoint(0,-150,mPaint);
        }
        int num = (int) (t/0.05);
        float s,y,x;
        switch(num){
            default:
            case 3:
                x = t-0.15f*(1-t);
                s = mPathMeasure.getLength();
                y = -s*x*x+2*s*x;
                mPathMeasure.getSegment(y,y+1,dst,true);
            case 2:
                x = t-0.10f*(1-t);
                s = mPathMeasure.getLength();
                y = -s*x*x+2*s*x;
                mPathMeasure.getSegment(y,y+1,dst,true);
            case 1:
                x = t-0.05f*(1-t);
                s = mPathMeasure.getLength();
                y = -s*x*x+2*s*x;
                mPathMeasure.getSegment(y,y+1,dst,true);
            case 0:
                x = t;
                s = mPathMeasure.getLength();
                y = -s*x*x+2*s*x;
                mPathMeasure.getSegment(y,y+1,dst,true);
                break;
        }
        canvas.drawPath(dst,mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

}
