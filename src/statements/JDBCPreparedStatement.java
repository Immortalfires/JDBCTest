package statements;
import java.sql.*;
/**
 * @author:Aurevoir
 * @date: 2020/2/3  15:59
 * preparedStatement 对象使用
 */
public class JDBCPreparedStatement {
    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static final String user = "root";
    static final String password = "xrw20010922";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(JDBC_Driver);
            System.out.println("connecting to database...");
            conn = DriverManager.getConnection(DB_URL, user, password);
            System.out.println("creating statement...");

            String sql = "UPDATE employees set age=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 33);
            stmt.setInt(2, 7);

            int rows = stmt.executeUpdate();
            System.out.println("Rows impacted: " + rows);

            sql = "SELECT * FROM employees";
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
        }finally {
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
        System.out.println("Bye");
    }
}