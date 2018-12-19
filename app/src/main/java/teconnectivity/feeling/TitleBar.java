package teconnectivity.feeling;

import android.content.Context;
import android.content.res.TypedArray;

import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.view.Gravity;

import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import teconnectivity.feeling.R;

public class TitleBar extends RelativeLayout{

    private Button leftButton,rightButton;
    private TextView titleText;
    private LayoutParams leftParams,rightParams,titleParams;

    private String title;
    private int titleColor;
    private float titleSize;
    private int btnMargin,btn_v_margin,btn_h_margin;
    private int btn_height;
    private String rightText;
    private String leftText;
    private Drawable rightBackground;
    private Drawable leftBackground;

    public void setRightOnClickListener(OnClickListener onClickListener){
        rightButton.setOnClickListener(onClickListener);
    }

    public void setLeftOnClickListener(OnClickListener onClickListener){
        leftButton.setOnClickListener(onClickListener);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,  R.styleable.Topbar);
        title = ta.getString(R.styleable.Topbar_title);
        titleColor = ta.getColor(R.styleable.Topbar_title_color,0);
        titleSize = ta.getDimension(R.styleable.Topbar_title_size,0);
        btnMargin = (int) ta.getDimension(R.styleable.Topbar_btn_margin,0);
        btn_v_margin = (int) ta.getDimension(R.styleable.Topbar_btn_vertical_margin,0);
        btn_h_margin = (int) ta.getDimension(R.styleable.Topbar_btn_horizontal_margin,0);

        btn_height = (int) ta.getDimension(R.styleable.Topbar_btn_height,0);

        rightText = ta.getString(R.styleable.Topbar_right_text);
        leftText = ta.getString(R.styleable.Topbar_left_text);
        rightBackground = ta.getDrawable(R.styleable.Topbar_right_background);
        leftBackground = ta.getDrawable(R.styleable.Topbar_left_background);
        btn_v_margin = (int) ta.getDimension(R.styleable.Topbar_btn_vertical_margin,0);
        btn_h_margin = (int) ta.getDimension(R.styleable.Topbar_btn_horizontal_margin,0);

        ta.recycle();//回收资源，防止内存泄漏

        leftButton = new Button(context);
        rightButton = new Button(context);
        titleText = new TextView(context);

        leftButton.setText(leftText);
        leftButton.setTextColor(titleColor);
        leftButton.setBackground(leftBackground);
        leftButton.setPadding(10,0,10,0);

        rightButton.setText(rightText);
        rightButton.setTextColor(titleColor);
        rightButton.setBackground(rightBackground);
        rightButton.setPadding(10,0,10,0);

        titleText.setText(title);
        titleText.setTextColor(titleColor);
        titleText.setTextSize(titleSize);
        titleText.setGravity(Gravity.CENTER);

        if(btn_v_margin == 0){
            btn_v_margin = btnMargin;
        }
        if(btn_h_margin == 0){
            btn_h_margin = btnMargin;
        }

        leftParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        leftParams.setMargins(btn_h_margin,btn_v_margin,0,btn_v_margin);
        //设置button高度，Android 默认高度太高。
        if(btn_height!=0)leftParams.height = btn_height;
        addView(leftButton,leftParams);

        rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        rightParams.setMargins(0,btn_v_margin,btn_h_margin,btn_v_margin);
        if(btn_height!=0)rightParams.height = btn_height;
        addView(rightButton,rightParams);

        titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(titleText,titleParams);
    }
}
