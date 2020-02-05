package resultSet;

import java.sql.*;

/**
 * @author:Aurevoir
 * @date: 2020/2/1  14:26
 * 浏览结果集
 */
public class NavigatingResultSet {
    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static final String user = "root";
    static final String password = "xrw20010922";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_Driver);
            System.out.println("connecting to database...");

            conn = DriverManager.getConnection(DB_URL,user,password);
            System.out.println("creating statement...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "select * from employees";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Moving cursor to the last ...");
            rs.last();
            System.out.println("Displaying record...");
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            System.out.println("id: " + id +" ,name: " + name + " ,age: " + age);

            System.out.println("Moving cursor to the first row...");
            rs.first();
            System.out.println("Displaying record...");
            id = rs.getInt("id");
            name = rs.getString("name");
            age = rs.getInt("age");
            System.out.println("id: " + id +" ,name: " + name + " ,age: " + age);

            System.out.println("Moving cursor to the next row...");
            rs.next();
            System.out.println("Displaying record...");
            id = rs.getInt("id");
            name = rs.getString("name");
            age = rs.getInt("age");
            System.out.println("id: " + id +" ,name: " + name + " ,age: " + age);

            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null){
                    stmt.close();
                }
            }catch (SQLException se2){
                se2.printStackTrace();
            }

            try {
                if(conn!=null){
                    conn.close();
                }
            }catch (SQLException se2){
                se2.printStackTrace();
            }
        }
        System.out.println("Bye");
    }
}
