package example.com.zhouyi_20.activity.mine;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.com.zhouyi_20.R;

/**
 * Created by ChenSiyuan on 2018/10/11.
 */

public class info_item_edit extends LinearLayout {
    private TextView info_name;
    private EditText editText;
    public info_item_edit(Context context){
        this(context,null);
    }
    public info_item_edit(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }
    public info_item_edit(Context context,@Nullable AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.info_item_edit,this);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.info_item_edit);
        info_name=findViewById(R.id.info_name);
        editText=findViewById(R.id.info_text);
        info_name.setText(typedArray.getString(R.styleable.info_item_edit_show_name));
        initview();

    }
    private void initview(){
        this.setVisibility(View.VISIBLE);
    }



}
