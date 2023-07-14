import java.sql.*;

public interface BazaDanychModify {
    default PreparedStatement modyfikujBaze(String query) {
        PreparedStatement preparedStatement;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/przychodnia", "root", "");
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }
        return null;
    }
}
