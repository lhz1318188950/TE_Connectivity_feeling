package teconnectivity.feeling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    InetAddress address;
    //*********IP,端口**********//
    String IP = null;
    int port = 34162;

    //**************************//
    DatagramSocket socket = null;

    // String reply = "Connect to the server successfully.";
    String responseStr = "Data for the server.";
    String statusStr1 = "Waiting...";

    String strCMD = "eSIM*00";

    //*********控件声明处********//
    EditText stateET;
    EditText ms5637TmpET;
    EditText ms5637PressET;
    EditText kma36MaET;
    EditText kma36KreET;
    EditText htu21dTmpET;
    EditText htu21dHdyET;
    //EditText com_level;


    TextView srTV;
    TextView recvTimeTV;
    Spinner ismiSP;

//   connRunnable mm_connRunnable = new connRunnable();
//   sendCMDRunnable mm_sendCMDRunnable = new sendCMDRunnable();

    private static int flag = 0;
    private  boolean m_runFlag = true;

    // 用来存放从服务器端获取的JSON数据h
    JSONObject JsonObject = null;

    // IMSI号列表
    List<String> m_IMSI_all = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    // 当前IMSI号
    String CurrentIMSI = "460040436504597";
    String[] m_imsiAll = {"460040436504797","460042171609201"};

    // 传感器值变量声明
    String MS5637_TMP;
    String MS5637_PRESS;
    String KMA36_MA;
    String KMA36_KRE;
    String HTU21D_TMP;
    String HTU21D_HDY;
    //String testresult;

    String recvTime;


    private void setIconsVisible(Menu menu, boolean flag) {
        //判断menu是否为空
        if(menu != null) {
            try {
                //如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                //暴力访问该方法
                method.setAccessible(true);
                //调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final String TAG = "MainActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        setIconsVisible(menu, true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.postback:
                strCMD = "Post*00";
                srTV.setText("Postbacking state:");
                stateET.setText("Postback, waiting for the server to return...");
                ismiSP.setEnabled(false);
                break;

            case R.id.startup:
                m_runFlag = false;
                // mm_sendCMDRunnable.close();
                socket.close();
                Intent startupIntent = new Intent(MainActivity.this, ConnectActivity.class);
                // splashIntent.putExtra("remoteIP", m_remoteIP);
                MainActivity.this.startActivity(startupIntent);
                MainActivity.this.finish();

                break;

            // 点击返回键，出现退出APP确认提示框
            case android.R.id.home:
                AlertDialog.Builder m_Builder = new AlertDialog.Builder(this);
                m_Builder.setTitle("Exit confirmation").setIcon(R.drawable.break_chain).setMessage("Do you really exit the application?").setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                }).setPositiveButton(
                        "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                m_runFlag = false;
                                // mm_sendCMDRunnable.close();
                                socket.close();
                                finish();
                            }
                        }
                );
                m_Builder.create().show();
                break;

////            case R.id.reload:
////////                final Intent m_intentSP = getPackageManager().getLaunchIntentForPackage(getPackageName());
////////                System.out.println("getPackageName()：" + getPackageName());
////////                m_intentSP.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////////                startActivity(m_intentSP);
////
////                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_fragment = findViewById(R.id.button_fragment);                 //设置切换页面功能
        button_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                String context=ms5637TmpET.getText().toString();
                Intent intent_1 =DetailActivity.newIntent(MainActivity.this,context);
                startActivity(intent_1);
                startActivity(intent);
            }
        });
        
        Intent splashIntent = getIntent();
        IP = splashIntent.getStringExtra("remoteIP");

        System.out.println("Mainactivity----------IP----------"+IP);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // -----------变量与控件关联------------
        conFindView();

        // 从SplashActivity中获取数据
        // Intent splashIntent = getIntent();
        m_imsiAll = splashIntent.getStringArrayExtra("imsiALL-Online");

        // 将IMSI号加到List中
        for(int i = 0; i < m_imsiAll.length; i++){
            if(m_imsiAll[i].length() == 15) {
                m_IMSI_all.add(m_imsiAll[i]);
            }
        }

        // 设置IMSI Spinner的内容
        adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, m_IMSI_all);
        ismiSP.setAdapter(adapter);
        ismiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // mm_sendCMDRunnable.mRunning = false;
                srTV.setText("Receiving state:");
                stateET.setText("Waiting for new data...");
                // 传感器数据EditText初始化
                SensorsDataETInit();
                CurrentIMSI = ismiSP.getSelectedItem().toString();
                // CurrentIMSI = adapter.getItem(position);
                strCMD = "Card*" + CurrentIMSI;
                // mm_sendCMDRunnable.mRunning = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {strCMD = "Card*" + "460040436504597";}
        });

        // 传感器数据EditText初始化
        SensorsDataETInit();

        //连接服务器
        ConnectServer();

        if(null != ismiSP.getSelectedItem().toString()){
            strCMD = "Card*" + ismiSP.getSelectedItem().toString();
        }else{
            strCMD = "Card*"+ "460042171609201";
        }
        // mm_connRunnable.start();
        // mm_sendCMDRunnable.start();
        new Thread(m_sendCMDRunnable).start();
    }
    

    //此处用于写检测指数并更改图片



    public void conFindView(){
        // EditText变量与控件关联
        stateET    = (EditText)findViewById(R.id.stateET);
        srTV       = findViewById(R.id.srTV);
        recvTimeTV = (TextView) findViewById(R.id.recvTimeTV);

        ms5637TmpET   = (EditText)findViewById(R.id.ms5637TmpET);
        ms5637PressET = (EditText)findViewById(R.id.ms5637PressET);
        kma36MaET     = (EditText)findViewById(R.id.kma36MaET);
        kma36KreET    = (EditText)findViewById(R.id.kma36KreET);
        htu21dTmpET   = (EditText)findViewById(R.id.htu21dTmpET);
        htu21dHdyET   = (EditText)findViewById(R.id.htu21dHdyET);
        //com_level     = (EditText)findViewById(R.id.com_level);

        ismiSP = (Spinner) findViewById(R.id.spinner);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.close();
        // mm_sendCMDRunnable.close();
    }

    public void ConnectServer(){
        try {
            address = InetAddress.getByName(IP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // 3.创建DatagramSocket对象
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private Runnable m_connRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("SplashActivity:connRunnable-strCMD：" + strCMD);
            byte[] data = strCMD.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable m_sendCMDRunnable = new Runnable() {
        @Override
        public void run() {
            while (m_runFlag) {
                try {
                    Thread.sleep(3000);
                    Message msg = new Message();
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                    Thread.sleep(3000);
                    // 数据刷新频率
                    System.out.println("MainActivity:sendCMDRunable-strCMD：" + strCMD);
                    byte[] data = strCMD.getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    message();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


//    private class connRunnable extends Thread {
//        private boolean mcRunning = true;
//
//        @Override
//        public void run() {
//            if (mcRunning) {
//                System.out.println("MainActivity:connRunnable-strCMD：" + strCMD);
//                byte[] data = strCMD.getBytes();
//                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
//                try {
//                    socket.send(packet);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mcRunning = false;
//            }
//        }
//    }

//    private class sendCMDRunnable extends Thread {
//        private boolean mRunning = false;
//
//        @Override
//        public void run() {
//            mRunning = true;
//            while (mRunning) {
//                try {
//                    Thread.sleep(3000);
//                    Message msg = new Message();
//                    msg.what = 2;
//                    mHandler.sendMessage(msg);
//                    Thread.sleep(3000);
//                    // 数据刷新频率
//                    System.out.println("MainActivity:sendCMDRunable-strCMD：" + strCMD);
//                    byte[] data = strCMD.getBytes();
//                    DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
//                    try {
//                        socket.send(packet);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                   message();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        public void close() {
//            mRunning = false;
//        }
//    }

    public void message() throws InterruptedException {
        // 1.创建数据报，用于接收服务器端响应的数据
        byte[] data2 = new byte[1024];
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
        // 2.接收服务器响应的数据
        try {
            socket.receive(packet2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3.读取数据
        responseStr = new String(data2, 0, packet2.getLength());
        //***接收数据处，接收到的数据位JSON格式的，下面需要解析***//
        System.out.println("MainActivity:sendCMDRunable-strCMD：Getting the data from server：" + responseStr);
        Message msg = new Message();
        if(responseStr.equals("delete:1 updata:1") || responseStr.equals("delete:0 updata:1")){
            msg.what = 3;
            strCMD = "Card*" + ismiSP.getSelectedItem().toString();
        }
        else {
            //解析JSON数据
            try {
                JsonObject = new JSONObject(responseStr);

                MS5637_TMP   = JsonObject.getString("MS5637_TMP");//获取pet对象的参数
                MS5637_PRESS = JsonObject.getString("MS5637_PRESS");
                KMA36_MA      = JsonObject.getString("KMA36_MA");
                KMA36_KRE     = JsonObject.getString("KMA36_KRE");
                HTU21D_TMP    = JsonObject.getString("HTU21D_TMP");
                HTU21D_HDY    = JsonObject.getString("HTU21D_HDY");
                //testresult     = JsonObject.getString("testresult");

                recvTime = JsonObject.getString("sendTime");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // mm_sendCMDRunnable.close();
            //*****************************************************//
            msg.what = 1;
        }
        mHandler.sendMessage(msg);

    }

    /*-------------------------------------------------------------
    // SensorsDataETInit()
    // 传感器数据EditText初始化
    //
     */
    @SuppressLint("ResourceAsColor")
    private void SensorsDataETInit()
    {
        recvTimeTV.setText("--");

        ms5637TmpET.setText("--");
        ms5637PressET.setText("--");
        kma36MaET.setText("--");
        kma36KreET.setText("--");
        htu21dTmpET.setText("--");
        htu21dHdyET.setText("--");
        //com_level.setText("--");

        ms5637TmpET.setTextColor(R.color.black);
        ms5637PressET.setTextColor(R.color.black);
        kma36MaET.setTextColor(R.color.black);
        kma36KreET.setTextColor(R.color.black);
        htu21dTmpET.setTextColor(R.color.black);
        htu21dHdyET.setTextColor(R.color.black);
        //com_level.setTextColor(R.color.black);
    }

    private Handler mHandler = new Handler() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //****************更新数据处*****************//
                    double tmp;
                    String strTmp;
                    srTV.setText("Receiving state:");
                    stateET.setText("Receive data successfully.");
                    if(0 == MS5637_TMP.length() || 0 == MS5637_PRESS.length() || 0 ==  KMA36_MA
                            .length() ||  0 ==  KMA36_KRE.length() || 0 == HTU21D_TMP.length() ||
                            0 == HTU21D_HDY.length()){
                        Toast.makeText(MainActivity.this, "The data is unknown, please check " + "...",  Toast
                                .LENGTH_SHORT).show();

                        SensorsDataETInit();
                        recvTimeTV.setText("--");
                    }
                    else{
                        tmp = Double.parseDouble(MS5637_TMP);
                        strTmp = Double.toString(tmp / 10.0);
                        ms5637TmpET.setText(strTmp);
                        ms5637TmpET.setTextColor(R.color.red);

                        tmp = Double.parseDouble(MS5637_PRESS);
                        strTmp = Double.toString(tmp / 10.0);
                        ms5637PressET.setText(strTmp);
                        ms5637PressET.setTextColor(R.color.red);

                        tmp = Double.parseDouble(KMA36_MA);
                        strTmp = Double.toString(tmp / 10.0);
                        kma36MaET.setText(strTmp);
                        kma36MaET.setTextColor(R.color.red);

                        tmp = Double.parseDouble(KMA36_KRE);
                        strTmp = Double.toString(tmp / 10.0);
                        kma36KreET.setText(strTmp);
                        kma36KreET.setTextColor(R.color.red);

                        tmp = Double.parseDouble(HTU21D_TMP);
                        strTmp = Double.toString(tmp / 10.0);
                        htu21dTmpET.setText(strTmp);
                        htu21dTmpET.setTextColor(R.color.red);

                        tmp = Double.parseDouble(HTU21D_HDY);
                        strTmp = Double.toString(tmp / 10.0);
                        htu21dHdyET.setText(strTmp);
                        htu21dHdyET.setTextColor(R.color.red);


                        recvTimeTV.setText(recvTime);
                        // mm_sendCMDRunnable.mRunning = true;
                    }

                    //******************************************//
                    break;
                case 2:
                    stateET.setText("Waiting for new data...");
                    // mm_sendCMDRunnable.mRunning = true;
                    //SensorsDataETInit();
                    break;
                case 3:
                    statusStr1 = "Postback the data successfully.";
                    // Toast.makeText(this, statusStr1, Toast.LENGTH_LONG).show();
                    // stateET.setTextColor(R.color.black);
                    stateET.setText(statusStr1);
                    ismiSP.setEnabled(true);
                    // mm_sendCMDRunnable.mRunning = true;
                    break;
                case 4:

                case 5:

                default:
                    break;
            }
        }
    };

}
