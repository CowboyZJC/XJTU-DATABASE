import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;



public class Insert_S040 {
    public static void main(String[] args) {
        List<String[]> Reader_csv = new CSVread().ReadMethod("src/S040_1000.csv");
        Connection conn = new Connection_Gauss().get_Connection("1315");
        int len = Reader_csv.toArray().length;
        String snumber = "";
        String sname = "";
        String sex = "";
        String bdate = "";
        String height = "";
        String dorm = "";
        for (int i = 0; i < len; i++) {
            snumber = Reader_csv.get(i)[0];
            sname = Reader_csv.get(i)[1];
            sex = Reader_csv.get(i)[2];
            bdate = Reader_csv.get(i)[3];
            height = Reader_csv.get(i)[4];
            dorm = Reader_csv.get(i)[5];
            new Insert_S040().insert(conn, snumber, sname, sex, bdate, height, dorm);
        }
        try {
            conn.close();
            System.out.println("结束");
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
    public void insert(Connection conn, String snumber, String sname, String sex, String bdate, String height, String dorm){
        String command = "insert into \"exp\".\"S040_1000\" (\"S#\",\"SNAME\",\"SEX\",\"BDATE\",\"HEIGHT\",\"DORM\")" +
                "values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(command);
            preparedStatement.setString(1,snumber);
            preparedStatement.setString(2,sname);
            preparedStatement.setString(3,sex);
            preparedStatement.setString(4,bdate);
            preparedStatement.setString(5,height);
            preparedStatement.setString(6,dorm);
            preparedStatement.execute();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
}
