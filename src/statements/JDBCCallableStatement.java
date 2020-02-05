package statements;
import java.sql.*;
/**
 * @author:Aurevoir
 * @date: 2020/2/3  16:07
 * CallableStatement 对象使用
 */
public class JDBCCallableStatement {
    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static final String user = "root";
    static final String password = "xrw20010922";

    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            Class.forName(JDBC_Driver);
            System.out.println("connecting to database...");
            conn = DriverManager.getConnection(DB_URL, user, password);
            System.out.println("creating statement...");
            String sql = "{call getEmpName(?,?)}";
            stmt = conn.prepareCall(sql);

            int empId=3;
            stmt.setInt(1,empId);
            stmt.registerOutParameter(2, Types.VARCHAR);
            System.out.println("Executing stored procedure...");
            stmt.execute();

            String empName = stmt.getString(2);
            System.out.println("employee's name with ID: "+empId + " is " + empName);
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(stmt!=null)
                    stmt.close();
            }catch (SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch (SQLException se2){
            }
        }
        System.out.println("Bye");
    }
}
