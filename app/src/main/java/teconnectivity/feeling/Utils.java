package teconnectivity.feeling;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Utils {                             //这是一个数据库链接文件
    private InetAddress address;
    //*********IP,端口**********//
    private String IP = "122.112.217.197";
    private int port = 34160;

    private DatagramSocket socket = null;
    private String reply="连接服务器成功";

    public Utils(InetAddress address, String IP, int port, DatagramSocket socket, String reply) {
        this.address = address;
        this.IP = IP;
        this.port = port;
        this.socket = socket;
        this.reply = reply;
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
}
