import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test6 {
    public static void main(String[] args) throws SQLException {
        Connection conn = new Connection_Gauss().get_Connection("7654");
        String command = "SELECT \"C#\" FROM \"exp\".\"C040_extend\" WHERE LEFT(\"C#\",4) = 'COMP'";
        PreparedStatement preparedStatement = conn.prepareStatement(command);
        preparedStatement.execute();
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()) {
            try {
                System.out.println(resultSet.getObject("C#"));
                command = "INSERT INTO \"exp\".\"SC040_extend\" (\"S#\",\"C#\",\"GRADE\") VALUES ";
                command = command + "("+"'"+"2215611146"+"'"+","+"'"+resultSet.getObject("C#").toString()+"'"+","+"95.0"+")";
                preparedStatement = conn.prepareStatement(command);
                preparedStatement.execute();
                System.out.println("成功");
            }
            catch (SQLException se){
                se.printStackTrace();
            }
        }
    }
}