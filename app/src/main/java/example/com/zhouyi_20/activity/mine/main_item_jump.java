package example.com.zhouyi_20.activity.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import example.com.zhouyi_20.R;

/**
 * BonakLiu
 * 主活动下面三个按钮
 */
public class main_item_jump extends LinearLayout {
    private ImageView bottomImage;
    public main_item_jump(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        LayoutInflater.from(context).inflate(R.layout.main_item_jump, this);
    }
}
