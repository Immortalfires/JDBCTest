package JDBCUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author:Aurevoir
 * @date: 2020/2/5  14:16
 * JDBCUtils工具类（注册驱动，关闭资源，数据库连接池）
 */
public class JDBCUtils {
    private static final String URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "xrw20010922";

    //数据库连接池
    private static ArrayList<Connection> conList = new ArrayList<>();

    static{
        for(int i=0;i<5;i++){
            Connection conn = getConnection();
            conList.add(conn);
        }
    }

    public static Connection getConnections(){
        if(!conList.isEmpty()){
            Connection con = conList.get(0);
            conList.remove(con);
            return con;
        }else {
            return getConnection();
        }
    }

    /**
     * 注册驱动，获得与数据库通信的Connection对象
     * @return  Connection对象
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭资源
     * @param rs 结果集
     * @param stmt Statement对象
     * @param conn Connection对象
     */
    public static void close(ResultSet rs, Statement stmt ,Connection conn){
         try {
             if (rs != null)
                 rs.close();
         }catch (SQLException se){
             se.printStackTrace();
         }

         try{
             if (stmt!=null)
                 stmt.close();
         }catch (SQLException se){
             se.printStackTrace();
         }

         if(conn!=null)
                 conList.add(conn);
    }

    /**
     * 关闭资源
     * @param rs 结果集
     * @param stmt  PreparedStatement对象
     * @param conn  Connection对象
     */
    public static void close(ResultSet rs, PreparedStatement stmt ,Connection conn){
        try {
            if (rs != null)
                rs.close();
        }catch (SQLException se){
            se.printStackTrace();
        }

        try{
            if (stmt!=null)
                stmt.close();
        }catch (SQLException se){
            se.printStackTrace();
        }

        if(conn!=null)
                conList.add(conn);
    }

    /**
     * 关闭资源
     * @param rs 结果集
     * @param stmt CallableStatement对象
     * @param conn Connection对象
     */
    public static void close(ResultSet rs, CallableStatement stmt ,Connection conn){
        try {
            if (rs != null)
                rs.close();
        }catch (SQLException se){
            se.printStackTrace();
        }

        try{
            if (stmt!=null)
                stmt.close();
        }catch (SQLException se){
            se.printStackTrace();
        }

        if(conn!=null)
                conList.add(conn);
    }
}
