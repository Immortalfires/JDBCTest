package JDBCConnectionPool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;
/**
 * @author:Aurevoir
 * @date: 2020/2/5  18:43
 * 使用c3p0连接池 操作数据库
 */
public class JDBCC3p0Datasource {
    private static final String URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "xrw20010922";
    private static ComboPooledDataSource dataSource;

    static {
        try{
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl(URL);
            dataSource.setUser(user);
            dataSource.setPassword(password);

            dataSource.setInitialPoolSize(5);
            dataSource.setMaxPoolSize(20);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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

        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
