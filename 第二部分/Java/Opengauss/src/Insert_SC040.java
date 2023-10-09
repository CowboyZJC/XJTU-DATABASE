import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class Insert_SC040 {
    public static void main(String[] args) {
        List<String[]> Reader_csv = new CSVread().ReadMethod("src/SC040.csv");
        Connection conn = new Connection_Gauss().get_Connection("7654");
        int len = Reader_csv.toArray().length;
        int len_row = 0;
        String snumber = "";
        String cnumber = "";
        String grade = "";
        for (int i = 0; i < len; i++) {
            len_row = Reader_csv.get(i).length;
            snumber = Reader_csv.get(i)[0];
            cnumber = Reader_csv.get(i)[1];
            if(len_row >= 3){
                grade = Reader_csv.get(i)[2];
            }
            else{
                grade = "NULL";
            }
            new Insert_SC040().insert(conn, snumber, cnumber, grade);
        }
        try {
            conn.close();
            System.out.println("结束");
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
    public void insert(Connection conn, String snumber, String cnumber, String grade){
        String command = "insert into \"exp\".\"SC040_extend\" (\"S#\",\"C#\",\"GRADE\") values ";
        command = command + "(" + "'" +snumber + "'" +  ',' + "'" + cnumber + "'" + ',' + grade + ")";
        System.out.println(command);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(command);
            preparedStatement.execute();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
}
