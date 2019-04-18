package example.com.zhouyi_20.activity.total_result;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import example.com.zhouyi_20.R;

/**
 * Created by ChenSiyuan on 2019/1/16.
 * The class used to paint the point when change the fragment
 */

public class Change_Point extends View {
    private int r=9;
    private boolean isSelected = false;
    private Paint mPaint;
//paint point
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.colorUnselected));
    }
    public Change_Point (Context context){
        super(context);
        initPaint();
    }
    public Change_Point (Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        initPaint();
    }
    public Change_Point (Context context,@Nullable AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initPaint();
    }

    //set point's color in different station
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(isSelected){
            mPaint.setColor(getResources().getColor(R.color.colorSelected));
        }else {
            mPaint.setColor(getResources().getColor(R.color.colorUnselected));
        }
        canvas.drawCircle(getWidth()/2,getHeight()/2,r,mPaint);
    }
    @Override
    public void setSelected(boolean selected){
        isSelected = selected;
        invalidate();
    }
}
