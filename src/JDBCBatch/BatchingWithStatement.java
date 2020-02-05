package JDBCBatch;

import java.sql.*;

/**
 * @author:Aurevoir
 * @date: 2020/2/5  11:57
 * Statement对象 批处理
 */
public class BatchingWithStatement {
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
            printRows(stmt);

            String SQL = "INSERT INTO employees(id,name,age) VALUES (9,'Red',56)";
            stmt.addBatch(SQL);
            SQL = "UPDATE employees SET age=23 WHERE id=1";
            stmt.addBatch(SQL);

            int[] count = stmt.executeBatch();
            conn.commit();
            printRows(stmt);

            stmt.close();
            conn.close();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Bye");
    }

    private static void printRows(Statement stmt) throws SQLException{
        System.out.println("Displaying available rows...");
        String sql = "SELECT * FROM employees";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            System.out.println("id: " + id + " ,name: " + name + " ,age: " + age);
        }
        rs.close();
    }
}
