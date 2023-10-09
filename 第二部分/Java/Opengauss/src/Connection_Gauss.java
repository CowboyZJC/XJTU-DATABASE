import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Connection_Gauss {
    static final String user = "exp";
    static final String pwd = "zJc879544688";
    static final String driver = "org.postgresql.Driver";
    public static void main(String[] args){
        new Connection_Gauss().get_Connection("7654");
    }
    public Connection get_Connection(String port){
        try {
            Class.forName(driver);
        } catch (Exception var9) {
            var9.printStackTrace();
            return null;
        }
        Connection conn = null;
        String sourceURL = "jdbc:postgresql://127.0.0.1:"+port+"/postgres?exp";
        System.out.println("连接数据库...");
        try{
            conn = DriverManager.getConnection(sourceURL,user,pwd);
            System.out.println("成功");
        }
        catch (SQLException e){
            ;
        }
        return conn;
    }
}
