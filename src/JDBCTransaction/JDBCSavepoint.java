package JDBCTransaction;

import java.sql.*;

/**
 * @author:Aurevoir
 * @date: 2020/2/4  14:09
 */
public class JDBCSavepoint {
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
            stmt = conn.createStatement();

            String sql="SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("List result set for reference...");
            printRs(rs);
            //设置第一个事务保存点
            Savepoint savepoint1 = conn.setSavepoint("ROWS_DELETED_1");
            System.out.println("Deleting row...");
            sql = "DELETE FROM employees WHERE ID=4";
            stmt.executeUpdate(sql);
            conn.rollback(savepoint1);
            //设置第二个事务保存点
            Savepoint savepoint2 = conn.setSavepoint("ROWS_DELETED_2");
            System.out.println("Deleting row...");
            sql = "DELETE FROM employees WHERE ID=6";
            stmt.executeUpdate(sql);
            conn.rollback(savepoint2);
            //查看是否回滚成功
            sql = "SELECT * FROM employees";
            rs = stmt.executeQuery(sql);
            printRs(rs);
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException se){
            se.printStackTrace();
            System.out.println("Rolling back data here...");
            try{
                if(conn!=null)
                    conn.rollback();
            }catch (SQLException se2){
                se2.printStackTrace();
            }
        }catch (Exception e){
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
