package JDBCUtils;
import java.sql.*;

/**
 * @author:Aurevoir
 * @date: 2020/2/5  17:03
 * 利用utils工具类实现的简单例子
 */
public class JDBCUtilsExample {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            System.out.println("Connecting to database...");
            conn = JDBCUtils.getConnection();
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM employees";
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println("id: " + id +" ,name: " + name + " ,age: " + age);
            }
        }catch (Exception e){
        e.printStackTrace();
        }finally {
            JDBCUtils.close(rs,stmt,conn);
        }
        System.out.println("BYE!");
    }
}
