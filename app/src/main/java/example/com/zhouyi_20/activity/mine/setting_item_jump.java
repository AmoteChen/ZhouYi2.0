package example.com.zhouyi_20.activity.mine;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.com.zhouyi_20.R;

/**
 * create by Bonak 2019/5/11
 */
public class setting_item_jump extends LinearLayout {
    private TextView text_zuo;
    private TextView text_you;
    private CheckBox checkBox;
    public setting_item_jump(Context context){
        this(context,null);
    }
    public setting_item_jump(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public setting_item_jump(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.setting_item_jump,this);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.setting_item_jump);
        text_zuo=findViewById(R.id.setting_textZuo);
        text_you=findViewById(R.id.setting_textYou);
        checkBox = (CheckBox) findViewById(R.id.setting_kaiguan);
        
        text_zuo.setText(typedArray.getText(R.styleable.setting_item_jump_setting_item_zuo));
        text_you.setText(typedArray.getText(R.styleable.setting_item_jump_setting_item_you));
        checkBox.setVisibility(typedArray.getInt(R.styleable.setting_item_jump_setting_item_check,View.GONE));
    }
}
