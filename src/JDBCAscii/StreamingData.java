package JDBCAscii;
import java.io.*;
import java.sql.*;

/**
 * @author:Aurevoir
 * @date: 2020/2/5  12:37
 * 流式数据传输
 */
public class StreamingData {
    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static final String user = "root";
    static final String password = "xrw20010922";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_Driver);
            System.out.println("connecting to database...");
            conn = DriverManager.getConnection(DB_URL, user, password);
            //关闭事务自动提交，改为手动提交
            conn.setAutoCommit(false);

            stmt = conn.createStatement();
            createXMLTable(stmt);

            File file = new File("D:\\IdeaProjects\\JDBCtest\\src\\JDBCAscii\\xml_data.xml");
            long fileLength = file.length();
            FileInputStream fileInputStream = new FileInputStream(file);

            String SQL = "INSERT INTO XML_Data VALUES (?,?)";
            preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setInt(1,12);
            preparedStatement.setAsciiStream(2,fileInputStream,(int)fileLength);
            preparedStatement.execute();
            fileInputStream.close();

            SQL = "SELECT  Data FROM XML_Data WHERE id=12";
            rs = stmt.executeQuery(SQL);
            if(rs.next()){
                InputStream is = rs.getAsciiStream(1);
                int c;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while((c=is.read())!=-1)
                    bos.write(c);
                System.out.println(bos.toString());
            }
            conn.commit();
            rs.close();
            stmt.close();
            preparedStatement.close();
            conn.close();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Bye");
    }

    private static void createXMLTable(Statement stmt) throws SQLException {
        System.out.println("Creating XML_Data table...");
        String streamingDataSql = "CREATE TABLE XML_Data(id INTEGER ,Data Long)";
        try{
            stmt.executeUpdate("DROP TABLE XML_Data");
        }catch (SQLException e){
            e.printStackTrace();
        }
        stmt.executeUpdate(streamingDataSql);
    }
}
