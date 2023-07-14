import java.sql.*;

public interface BazaDanychConnect {
    default ResultSet polaczBaze(String query) {
        ResultSet resultSet;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/przychodnia", "root", "");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Blad pobierania danych z tabeli");
        }
        return null;
    }
}