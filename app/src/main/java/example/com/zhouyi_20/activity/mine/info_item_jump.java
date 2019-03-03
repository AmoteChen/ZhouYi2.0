package example.com.zhouyi_20.activity.mine;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.com.zhouyi_20.R;

/**
 * Created by ChenSiyuan on 2018/10/11.
 */

public class info_item_jump extends LinearLayout {
    private TextView info_name2;
    private TextView info_text2;
    public info_item_jump(Context context){
        this(context,null);
    }
    public info_item_jump(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }
    public info_item_jump(Context context,@Nullable AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.info_item_jump,this);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.info_item_jump);
        info_name2=findViewById(R.id.super_info_name);
        info_text2=findViewById(R.id.super_info_text);
        info_name2.setText(typedArray.getString(R.styleable.info_item_jump_show_name2));
        initview();

    }
    private void initview(){
        this.setVisibility(View.VISIBLE);
    }


}
