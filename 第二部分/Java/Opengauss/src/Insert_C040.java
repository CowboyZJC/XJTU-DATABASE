import java.sql.*;
import java.util.List;

public class Insert_C040 {
    public static void main(String[] args) {
        List<String[]> Reader_csv = new CSVread().ReadMethod("src/C040_100.csv");
        Connection conn = new Connection_Gauss().get_Connection("7654");
        int len = Reader_csv.toArray().length;
        int len_str = 0;
        String teacher = "";
        String cname = "";
        String cnumber = "";
        String period = "";
        String credit = "";
        for (int i = 0; i < len; i++) {
            len_str = Reader_csv.get(i).length;
            teacher = "";
            cnumber = Reader_csv.get(i)[0];
            cname = Reader_csv.get(i)[1];
            period = Reader_csv.get(i)[2];
            credit = Reader_csv.get(i)[3];
            for (int j = 4; j < len_str; j++) {
                if (j == len_str - 1 ) {
                    teacher += Reader_csv.get(i)[j];
                    teacher = teacher.replace("\"","");
                }
                else {
                        teacher += Reader_csv.get(i)[j] + ",";
                }
            }
            System.out.println(cname);
            new Insert_C040().insert(conn, cnumber, cname, period, credit, teacher);
        }
        try {
            conn.close();
            System.out.println("结束");
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
    public void insert(Connection conn, String cnumber, String cname, String period, String credit, String teacher){
        String command = "insert into \"exp\".\"C040_100\" (\"C#\",\"CNAME\",\"PERIOD\",\"CREDIT\",\"TEACHER\") values ";
        command = command + "(" + "'" +cnumber +"'" + "," + "'" + cname + "'" + "," + period  + "," + credit  + "," + "'" + teacher + "'" + ")";
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



