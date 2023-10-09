import java.sql.*;

public class Delete_SC040 {
    public static void main(String[] args){
        Connection conn = new Connection_Gauss().get_Connection("7654");
        String command_select = "SELECT * FROM \"exp\".\"SC040_extend\" WHERE \"GRADE\" < 60 ORDER BY RANDOM() LIMIT 200;";
        String command_delete = "DELETE FROM \"exp\".\"SC040_extend\" WHERE ";
        String snumber = null;
        String cnumber = null;
        String grade = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(command_select);
            preparedStatement.execute();
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                command_delete = "DELETE FROM \"exp\".\"SC040_extend\" WHERE ";
                snumber = resultSet.getObject("S#").toString();
                cnumber = resultSet.getObject("C#").toString();
                grade = resultSet.getObject("GRADE").toString();
                System.out.println(snumber+"  "+cnumber +"  "+grade);
                command_delete = command_delete + "\"S#\" = " + "'" + snumber + "'"+ " AND " + "\"C#\" = " + "'" + cnumber + "'" + " AND " + "\"GRADE\" = "+grade;
                System.out.println(command_delete);
                Statement statement = conn.createStatement();
                statement.execute(command_delete);
            }

            conn.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

}
