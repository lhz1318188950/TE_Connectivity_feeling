package teconnectivity.feeling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil
{
    private  String name=null;
    private  String pass=null;

    public DBUtil(String m,String p){
        this.name=m;
        this.pass=p;
    }
    private static Connection getSQLConnection(String ip, String user, String pwd, String db)
    {
        Connection con = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db + ";charset=utf8", user, pwd);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public String QuerySQL()
    {
        String result = "";
        try
        {
            Connection conn= getSQLConnection("122.112.217.197", "AHL-IoT-Suda", "Suda_DB", "CS_GUS");//根据自己的数据库信息填写对应的值
            String sql = "select username from userinfo  where userpwd=? and  username=? ";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, pass);
            stat.setString(2, name);
            ResultSet rs = stat.executeQuery();
            while (rs.next())
            {
                result= "1" ;
            }
            rs.close();
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            result += "查询数据异常!" + e.getMessage();
        }
        return result;
    }
    public static void main(String[] args)
    {
    }
}
