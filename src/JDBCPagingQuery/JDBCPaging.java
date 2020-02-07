package JDBCPagingQuery;
import java.sql.*;

/**
 * @author:Aurevoir
 * @date: 2020/2/5  13:58
 * JDBC 分页查询
 */
public class JDBCPaging {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static final String user = "root";
    static final String password = "xrw20010922";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int pageNumber = 2,pageCount = 8;
        try {
            //注册驱动
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, user, password);
            System.out.println("Creating statement...");

            String sql = "select * from employees limit ?,?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,(pageNumber -1)*pageCount);
            stmt.setInt(2,pageCount);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println("id: " + id +" ,name: " + name + " ,age: " + age);
            }
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
