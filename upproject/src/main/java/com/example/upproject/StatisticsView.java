package com.example.upproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author yangqiangyu on 5/28/16 19:20
 * @csdn博客 http://blog.csdn.net/yissan/article/details/51542455
 * @doc 一个简单的自定义图表显示View
 */
public class StatisticsView extends View {
    private Paint mBorderPaint; //画横纵轴
    private Paint circlePaint; //画坐标点的圆心
    private Paint mPathPaint; //画折线图
    private Path mPath;
    private int maxValue = 100;//纵轴最大值
    private int dividerCount = 10;//纵轴分割数量
    private int perValue = maxValue/dividerCount;//纵轴每个单位值
    private String[] bottomStr = {}; //底部显示String
    private float[] values = {};//具体的值
    private float bottomGap;//底部横轴单位间距

    private String title = "";
    private float leftGap;

    private TextPaint textPaint;


    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        this.perValue = maxValue/dividerCount;
        invalidate();
    }


    public void setValues(float[] values) {
        this.values = values;
        requestLayout();
    }

    public void setDividerCount(int dividerCount) {
        this.dividerCount = dividerCount;
    }

    public void setPerValue(int perValue) {
        this.perValue = perValue;
    }

    public void setBottomStr(String[] bottomStr) {
        this.bottomStr = bottomStr;
    }

    public StatisticsView(Context context) {
        super(context);
    }

    public StatisticsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        bottomGap = getWidth()/(bottomStr.length+1);
        leftGap = getHeight()/(dividerCount+2);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode==MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY){
            setMeasuredDimension(widthSize,heightSize);
        }else if (widthMeasureSpec==MeasureSpec.EXACTLY){
            setMeasuredDimension(widthSize,widthSize);
        }else if (heightMeasureSpec==MeasureSpec.EXACTLY){
            setMeasuredDimension(heightSize,heightSize);
        }

    }

    public StatisticsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StatisticsView);

        maxValue =array.getInt(R.styleable.StatisticsView_maxValue,100);
        dividerCount = array.getInt(R.styleable.StatisticsView_dividerCount,10);
//        title = array.getString(R.styleable.StatisticsView_viewTitle);
//        if (TextUtils.isEmpty(title)){
//            title = "";
//        }

        int pathcolor = array.getColor(R.styleable.StatisticsView_pathColor,Color.BLACK);
        int lineColor = array.getColor(R.styleable.StatisticsView_lineColor,Color.BLACK);
        int textColor = array.getColor(R.styleable.StatisticsView_textColor,Color.BLACK);

        mBorderPaint = new Paint();
        circlePaint = new Paint();
        mPathPaint = new Paint();


        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);//设置平滑的边缘
        PathEffect effect=new DashPathEffect(new float[]{8,8,8,8},1);//这两句用于实线变虚线
        mBorderPaint.setPathEffect(effect);
        mBorderPaint.setColor(lineColor);
        mBorderPaint.setAlpha(50);
        mBorderPaint.setStrokeWidth(1);


        mPathPaint.setAntiAlias(true);
        mPathPaint.setColor(pathcolor);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(10);
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);

        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(dip2px(getContext(),12));
        mPath = new Path();

        circlePaint.setColor(textColor);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        array.recycle();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (bottomStr==null||bottomStr.length==0){
            return;
        }

        //画左边的线
       // canvas.drawLine(bottomGap,getHeight()-leftGap,bottomGap,leftGap,mBorderPaint);


        float fontHeight =(textPaint.getFontMetrics().descent-textPaint.getFontMetrics().ascent);


        //画线：drawline(float startx,float starty,float stopx,float stopy,Paint paint)
        //写字：drawText(String text, int start, int end, Paint paint)
        //画圆：drawCircle(float cx, float cy, float radius, Paint paint)
        //画下边线
        for (int i = 1;i<=bottomStr.length;i++){
            canvas.drawCircle(i*bottomGap,getHeight()-leftGap,6,circlePaint);
            canvas.drawText(bottomStr[i-1],i*bottomGap-(textPaint.measureText(bottomStr[i-1])/2),getHeight()-leftGap/2+fontHeight/2,textPaint);
        }

        canvas.drawText(title,bottomGap,leftGap/2,textPaint);
        for (int i = 1;i<=dividerCount+1;i=i+2){
             //画左边的字
            canvas.drawText(perValue*(i-1)+"",bottomGap/2-(textPaint.measureText(perValue*(i-1)+"")/2),(((dividerCount+2-i)))*leftGap+fontHeight/2,textPaint);

            //画横线
            Path path =new Path();
            path.moveTo(bottomGap,getHeight()-((i)*leftGap));
            path.lineTo(getWidth()-bottomGap,getHeight()-((i)*leftGap));
            canvas.drawPath(path,mBorderPaint);
          //  canvas.drawLine(bottomGap,getHeight()-((i)*leftGap),getWidth()-bottomGap,getHeight()-((i)*leftGap),mBorderPaint);
        }


        /**
         * 画轨迹
         * y的坐标点根据 y/leftGap = values[i]/perValue 计算
         *
         */
        for (int i = 0;i<values.length;i++){
            if (i==0){
                mPath.moveTo(bottomGap,(dividerCount+1)*leftGap-(values[i]*leftGap/perValue));
            }else{
                mPath.lineTo((i+1)*bottomGap,(dividerCount+1)* leftGap-(values[i]*leftGap/perValue));
            }
            /**
             * 画轨迹圆点
             */
           // canvas.drawCircle((i+1)*bottomGap,(dividerCount+1)*leftGap-(values[i]*leftGap/perValue),6,circlePaint);
        }
        canvas.drawPath(mPath,mPathPaint);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
