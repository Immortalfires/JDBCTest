package JDBCTransaction;
import java.sql.*;
/**
 * @author:Aurevoir
 * @date: 2020/2/4  13:45
 * JDBC事务提交 / 回滚
 */
public class CommitAndRollback {
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
            //关闭事务自动提交，改为手动提交
            conn.setAutoCommit(false);
            System.out.println("creating statement...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //插入
            System.out.println("Inserting one row...");
            String sql = "INSERT INTO employees VALUES(8,'piper',37)";
            stmt.executeUpdate(sql);
            //提交事务
            System.out.println("Committing data here...");
            conn.commit();
            //列出更新后的结果集
            sql = "SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("List result set for reference...");
            printRs(rs);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
            System.out.println("Rolling back data here...");
            try{
                if(conn!=null){
                    conn.rollback();
                }
            }catch (SQLException se2){
                se2.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Bye");
    }

    private static void printRs(ResultSet rs) throws SQLException {
        rs.beforeFirst();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            System.out.println("id: " + id + " ,name: " + name + " ,age: " + age);
        }
    }
}
