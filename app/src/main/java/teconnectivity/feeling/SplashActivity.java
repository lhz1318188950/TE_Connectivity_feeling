package teconnectivity.feeling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static java.lang.Thread.*;
import static java.lang.Thread.sleep;

public class SplashActivity extends Activity {

    private InetAddress address;
    //*********IP,端口**********//
    private String IP = null;

    private int port = 34162;

    private DatagramSocket socket = null;

    //---------------------------备用-----------------------------------
    static private int counter = 0;
    static Handler m_handler = new Handler();

    private boolean m_runFlag = true;
    //------------------------------------------------------------------

    // Splash页面显示时间
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    String m_strCMD = "eSIM*00";
    String responseStr;
    String[] m_imsiAll;

    // 用来存放从服务器端获取的JSON数据
    JSONObject JsonObject = null;
    JSONArray imsiJsonArray = null;

//    static connRunnable m_connRunnable = new connRunnable();
//    static sendCMDRunnable m_sendCMDRunnable = new sendCMDRunnable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent startupIntent = getIntent();
        IP = startupIntent.getStringExtra("remoteIP");        //在下拉列表中选择IP

        System.out.println("------IP----"+IP);

        ConnectServer();

        new Thread(mm_connRunnable).start();
        new Thread(mm_sendCMDRunnable).start();

//        m_connRunnable.start();
//        m_sendCMDRunnable.start();



        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(null != m_imsiAll) {
                    m_runFlag = false;
                    Toast.makeText(SplashActivity.this, "Connect the server successfully and got the UEs' IMSI ...", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    mainIntent.putExtra("imsiALL-Online", m_imsiAll);
                    mainIntent.putExtra("remoteIP", IP);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
                else{
                    AlertDialog.Builder builder  = new AlertDialog.Builder(SplashActivity.this);
                    builder.setTitle("Information" ) ;
                    builder.setIcon(R.mipmap.te_logo);
                    builder.setMessage("Can't get the UEs' IMSI, Please Check the network ..." ) ;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SplashActivity.this.finish();
                                }
                            }
                    );
                    builder.show();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onDestroy() {
        socket.close();
//        m_sendCMDRunnable.close();
//        m_connRunnable.mcRunning = false;
        m_runFlag = false;
        super.onDestroy();
    }

    /*-----------------------------------------------------------
    //  连接远程服务器
     ------------------------------------------------------------*/
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

    private Runnable mm_connRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("SplashActivity:connRunnable-strCMD：" + m_strCMD);
            byte[] data = m_strCMD.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable mm_sendCMDRunnable = new Runnable() {
        @Override
        public void run() {
            while (m_runFlag) {
                try {
                    // 数据刷新频率
                    System.out.println("SplashActivity----sendCMDRunable-------strCMD：" + m_strCMD);
                    // message();
                    byte[] data = m_strCMD.getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

                    message();
                    Thread.sleep(200);
                    try {
                        socket.send(packet);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };




//    private static class connRunnable extends Thread {
//        private boolean mcRunning = true;
//
//        @Override
//        public void run() {
//            if (mcRunning) {
//                System.out.println("SplashActivity:connRunnable-strCMD：" + m_strCMD);
//                byte[] data = m_strCMD.getBytes();
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

//    private static class sendCMDRunnable extends Thread{
//        private boolean mRunning = false;
//
//        @Override
//        public void run() {
//            mRunning = true;
//            while (mRunning) {
//                    try {
//                        // 数据刷新频率
//                        System.out.println("SplashActivity----sendCMDRunable-------strCMD：" + m_strCMD);
//                        // message();
//                        byte[] data = m_strCMD.getBytes();
//                        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
//
//                        message();
//                        Thread.sleep(200);
//                        try {
//                            socket.send(packet);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//            }
//        }
//
//        public void close() {
//            mRunning = false;
//        }
//    }

    public void message() throws InterruptedException {
        Message msg = new Message();
        m_Handler.sendMessage(msg);

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

        if(0 != responseStr.length()) {
            // m_sendCMDRunnable.close();
            m_runFlag = false;
            //***接收数据处，接收到的数据位JSON格式的，下面需要解析***//
            // System.out.println("SplashActivity: Server Response--------" + responseStr);
            try {
                JsonObject = new JSONObject(responseStr);
                System.out.println("SplashActivity: m_imsiAll--------" + responseStr);
                imsiJsonArray = JsonObject.optJSONArray("IMSI");

                m_imsiAll = new String[imsiJsonArray.length()];
                for(int i = 0; i < imsiJsonArray.length(); i++){
                    m_imsiAll[i] = imsiJsonArray.getString(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            // msg.what = 1;
        }
        // m_Handler.sendMessage(msg);
    }

    private Handler m_Handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //****************更新数据处*****************//

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                case 5:

                default:
                    break;
            }
        }
    };
}

