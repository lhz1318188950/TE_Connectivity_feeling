package teconnectivity.feeling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import teconnectivity.feeling.fragment.Fragment1_1;
import teconnectivity.feeling.fragment.Fragment1_2;
import teconnectivity.feeling.fragment.Fragment1_3;
import teconnectivity.feeling.fragment.Fragment1_4;
import teconnectivity.feeling.fragment.Fragment2_1;
import teconnectivity.feeling.fragment.Fragment2_2;
import teconnectivity.feeling.fragment.Fragment2_3;
import teconnectivity.feeling.fragment.Fragment2_4;
import teconnectivity.feeling.fragment.Fragment3;
import teconnectivity.feeling.fragment.Fragment4;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String context=getIntent().getStringExtra("context");      //接受mainactivity传来的
        Float tem = convertToFloat(context,0);

        BottomBar bottomBar = findViewById(R.id.bottom_bar);
        /*bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#5CF4FF")
                .setTitleSize(9)
                .setTitleIconMargin(5)
                .setIconWidth(20)
                .setIconHeight(20)
                .addItem(Fragment1_1.class,
                        "Home",
                        R.mipmap.home_a,
                        R.mipmap.home_b)

                .addItem(Fragment2_1.class,
                        "Recommend",
                        R.mipmap.recommend_b,
                        R.mipmap.reconmmend_a)
                .addItem(Fragment3.class,
                        "Monitor",
                        R.mipmap.feedback_b,
                        R.mipmap.feedback_a)
                .addItem(Fragment4.class,
                        "Setting",
                        R.mipmap.setting_b,
                        R.mipmap.setting_a)
                .build();*/


        if(tem<8.0)
        {
            bottomBar.setContainer(R.id.fl_container)
                    .setTitleBeforeAndAfterColor("#999999", "#5CF4FF")
                    .setTitleSize(9)
                    .setTitleIconMargin(5)
                    .setIconWidth(20)
                    .setIconHeight(20)
                    .addItem(Fragment1_1.class,
                            "Home",
                            R.mipmap.home_a,
                            R.mipmap.home_b)

                    .addItem(Fragment2_1.class,
                            "Recommend",
                            R.mipmap.recommend_b,
                            R.mipmap.reconmmend_a)
                    .addItem(Fragment3.class,
                            "Monitor",
                            R.mipmap.feedback_b,
                            R.mipmap.feedback_a)
                    .addItem(Fragment4.class,
                            "Setting",
                            R.mipmap.setting_b,
                            R.mipmap.setting_a)
                    .build();
        }
        else if(tem>=8.0&&tem<18.0)
        {
            bottomBar.setContainer(R.id.fl_container)
                    .setTitleBeforeAndAfterColor("#999999", "#5CF4FF")
                    .setTitleSize(9)
                    .setTitleIconMargin(5)
                    .setIconWidth(20)
                    .setIconHeight(20)
                    .addItem(Fragment1_3.class,
                            "Home",
                            R.mipmap.home_a,
                            R.mipmap.home_b)

                    .addItem(Fragment2_3.class,
                            "Recommend",
                            R.mipmap.recommend_b,
                            R.mipmap.reconmmend_a)
                    .addItem(Fragment3.class,
                            "Monitor",
                            R.mipmap.feedback_b,
                            R.mipmap.feedback_a)
                    .addItem(Fragment4.class,
                            "Setting",
                            R.mipmap.setting_b,
                            R.mipmap.setting_a)
                    .build();
        }
        else if(tem>=18&&tem<28)
        {
            bottomBar.setContainer(R.id.fl_container)
                    .setTitleBeforeAndAfterColor("#999999", "#5CF4FF")
                    .setTitleSize(9)
                    .setTitleIconMargin(5)
                    .setIconWidth(20)
                    .setIconHeight(20)
                    .addItem(Fragment1_2.class,
                            "Home",
                            R.mipmap.home_a,
                            R.mipmap.home_b)

                    .addItem(Fragment2_2.class,
                            "Recommend",
                            R.mipmap.recommend_b,
                            R.mipmap.reconmmend_a)
                    .addItem(Fragment3.class,
                            "Monitor",
                            R.mipmap.feedback_b,
                            R.mipmap.feedback_a)
                    .addItem(Fragment4.class,
                            "Setting",
                            R.mipmap.setting_b,
                            R.mipmap.setting_a)
                    .build();
        }
        else
        {
            bottomBar.setContainer(R.id.fl_container)
                    .setTitleBeforeAndAfterColor("#999999", "#5CF4FF")
                    .setTitleSize(9)
                    .setTitleIconMargin(5)
                    .setIconWidth(20)
                    .setIconHeight(20)
                    .addItem(Fragment1_4.class,
                            "Home",
                            R.mipmap.home_a,
                            R.mipmap.home_b)

                    .addItem(Fragment2_4.class,
                            "Recommend",
                            R.mipmap.recommend_b,
                            R.mipmap.reconmmend_a)
                    .addItem(Fragment3.class,
                            "Monitor",
                            R.mipmap.feedback_b,
                            R.mipmap.feedback_a)
                    .addItem(Fragment4.class,
                            "Setting",
                            R.mipmap.setting_b,
                            R.mipmap.setting_a)
                    .build();
        }

    }

    public static Intent newIntent(Context packageContext, String context){
        Intent intent=new Intent(packageContext,DetailActivity.class);
        intent.putExtra("CONTEXT",context);
        return intent;
    }
    public static float convertToFloat(String number, float defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }





    /*public void button_detail_click(View view){
        Button button_detail = findViewById(R.id.button_detail);
        if (button_detail != null)
        {
            if(button_detail.getVisibility()==view.VISIBLE)
            {
                button_detail.setVisibility(view.INVISIBLE);
            }
            else
            {
                button_detail.setVisibility(view.VISIBLE);
            }
        }
    }*/
}
