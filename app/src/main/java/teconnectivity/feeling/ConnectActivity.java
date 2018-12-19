package teconnectivity.feeling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectActivity extends AppCompatActivity {

    private List<Map<String, Object>> m_dataList;
    private SimpleAdapter m_adapter;
    //   实验室测试服务器："122.112.217.197"

    static private String[] remoteIP = {"119.3.58.123", "119.3.53.79", "119.3.56.47", "119.3.56.170"};

    static  String m_remoteIP = null;

    Spinner remoteIpSP = null;
    Button ipSelectBtn = null;
    TextView reIpTV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        // 控件声明
        remoteIpSP = findViewById(R.id.device_number);
        ipSelectBtn = findViewById(R.id.button_connect);         //按钮功能

        // 准备数据源
        m_dataList = new ArrayList<>();
        // 新建适配器
        m_adapter = new SimpleAdapter(this, getData(), R.layout.spinner_display_style, new String[]{"image","ip"}, new int[]{R.id.aupulu, R.id.reIp});
        // 加载适配器
        remoteIpSP.setAdapter(m_adapter);

        remoteIpSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reIpTV = remoteIpSP.getSelectedView().findViewById(R.id.reIp);
                m_remoteIP = reIpTV.getText().toString();
                System.out.println("-----------m_remoteIP--"+ m_remoteIP);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                reIpTV = remoteIpSP.getSelectedView().findViewById(R.id.reIp);
                m_remoteIP = reIpTV.getText().toString();
                System.out.println("-----------m_remoteIP"+ m_remoteIP);
            }
        });

        // 设置Button监听器
        ipSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent splashIntent = new Intent(ConnectActivity.this, SplashActivity.class);
                splashIntent.putExtra("remoteIP", m_remoteIP);
                ConnectActivity.this.startActivity(splashIntent);
                ConnectActivity.this.finish();
            }
        });

        Button button_return = findViewById(R.id.button_return_log);                 //设置切换sign up页面功能
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnectActivity.this,LogActivity.class);
                startActivity(intent);
            }
        });
    }
    private List<Map<String, Object>> getData(){
        for(int i = 0; i < remoteIP.length; i++){
            Map<String, Object> map = new HashMap<>();
            map.put("image", R.mipmap.te_logo);
            map.put("ip",remoteIP[i]);
            m_dataList.add(map);
        }
        return m_dataList;
    }
}
