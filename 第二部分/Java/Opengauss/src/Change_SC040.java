import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Change_SC040 {
    public static void main(String[] args) {
        Thread insertThread = new Thread(() -> {
            List<String[]> Reader_csv = new CSVread().ReadMethod("src/SC040_20000.csv");
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
                new Change_SC040().insert_SC(conn, snumber, cnumber, grade);
                System.out.println(snumber+" "+cnumber+" "+grade);
                Thread.yield();
            }
            try {
                conn.close();
                System.out.println("结束");
            }
            catch(SQLException se){
                se.printStackTrace();
            }
        });
        Thread deleteThread = new Thread(() -> {
            Connection conn = new Connection_Gauss().get_Connection("7654");
            for(int i=0;i<200;){
                if(new Change_SC040().Delete_SC(conn))i++;
                Thread.yield(); //睡眠0.1秒
            }
        });
        insertThread.start();
        deleteThread.start();
    }
    public void insert_SC(Connection conn, String snumber, String cnumber, String grade){
        String command = "insert into \"exp\".\"SC040_20000\" (\"S#\",\"C#\",\"GRADE\") values ";
        command = command + "(" + "'" +snumber + "'" +  ',' + "'" + cnumber + "'" + ',' + grade + ")";
        System.out.println(command);
        try {
                PreparedStatement preparedStatement = conn.prepareStatement(command);
                preparedStatement.execute();
        }
        catch (SQLException se){
            se.printStackTrace();
        }
    }
    public boolean Delete_SC(Connection conn) {
        String command = "SELECT COUNT(*) FROM \"exp\".\"SC040_20000\" WHERE \"GRADE\" < 60";
        int number = 0;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(command);
            preparedStatement.execute();
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getObject(1).toString());
            number = Integer.parseInt(resultSet.getObject(1).toString());
            if(number >= 100){
                command = "DELETE FROM \"exp\".\"SC040_20000\" WHERE (\"S#\",\"C#\",\"GRADE\")" +
                        " IN ( SELECT * FROM \"exp\".\"SC040_20000\" WHERE \"GRADE\"<60 ORDER BY RANDOM() LIMIT 1);";
                System.out.println(command);
                preparedStatement = conn.prepareStatement(command);
                preparedStatement.execute();
                return true;
            }
            else {

            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return false;
    }
}
