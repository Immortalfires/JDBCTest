package statements;
import java.sql.*;
/**
 * @author:Aurevoir
 * @date: 2020/2/3  15:48
 * statement 对象使用
 */
public class JDBCStatement {
    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static final String user = "root";
    static final String password = "xrw20010922";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_Driver);
            System.out.println("connecting to database...");

            conn = DriverManager.getConnection(DB_URL, user, password);
            System.out.println("creating statement...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "UPDATE employees set age=20 where id=6";
            //该方法如果可以检索到ResultSet对象，则返回一个布尔值true; 否则返回false
            Boolean ret = stmt.execute(sql);
            System.out.println("Return value is:" + ret.toString());
            //该方法返回受sql执行影响的行数
            int rows = stmt.executeUpdate(sql);
            System.out.println("Rows impacted:" + rows);
            //该方法返回一个ResultSet对象
            sql = "SELECT * from employees";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println("id: " + id + " ,name: " + name + " ,age: " + age);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("There are something wrong!");
    }
}
