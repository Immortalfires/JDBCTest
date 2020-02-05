package resultSet;

import java.sql.*;

/**
 * @author:Aurevoir
 * @date: 2020/2/3  15:13
 * 更新结果集
 */
public class updatingResultSet {
    static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static final String user = "root";
    static final String password="xrw20010922";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,user,password);

            System.out.println("Creating statement...");
            //创建光标可向前向后滚动 且对修改敏感的  可更新的 结果集
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            String sql = "select * from employees";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("List result set for reference...");
            printRs(rs);

            rs.beforeFirst();
            while(rs.next()){
                int newAge = rs.getInt("age")+2;
                rs.updateDouble("age",newAge);
                rs.updateRow();
            }
            System.out.println("List result set showing new ages...");
            printRs(rs);

            System.out.println("Inserting a new record");
            rs.moveToInsertRow();
            rs.updateInt("id",7);
            rs.updateString("name","Alex");
            rs.updateInt("age",46);
            rs.insertRow();
            System.out.println("List result set showing new set...");
            printRs(rs);

            rs.absolute(2);
            System.out.println("List the record before deleting...");
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String name = rs.getString("name");
            System.out.println("id: " + id +" ,name: " + name + " ,age: " + age);
            rs.deleteRow();
            System.out.println("List result set after deleting one records");
            printRs(rs);

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    public static void printRs(ResultSet rs) throws SQLException {
        rs.beforeFirst();
        while(rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            System.out.println("id: " + id +" ,name: " + name + " ,age: " + age);
        }
    }
}
