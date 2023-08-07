import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DostepneWizytyFrame extends JFrame  implements BazaDanychConnect{

    int idPacjenta;
    private final JPanel wizytyPanel;
    private final JTextField idLekarzaTextField;

    public DostepneWizytyFrame(int idPacjenta) {
        super("Dostępne wizyty");
        this.idPacjenta = idPacjenta;

        // Ustawienia ramki
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(30,30));

        // Tworzenie komponentów
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton wyszukajButton = new JButton("Wyszukaj");
        wyszukajButton.setFocusPainted(false);
        bottomPanel.add(new JLabel("ID lekarza:"));
        idLekarzaTextField = new JTextField(15);
        bottomPanel.add(idLekarzaTextField);
        bottomPanel.add(wyszukajButton);
        add(bottomPanel, BorderLayout.NORTH);

        wizytyPanel = new JPanel(new GridLayout(0, 1));
        add(wizytyPanel, BorderLayout.CENTER);

        //tworzenie panelu na przycisk powrotu
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton button = new JButton("Powrót");
        button.addActionListener(e -> {
            WizytyPacjenta wP = new WizytyPacjenta(idPacjenta);
            wP.setVisible(true);
            setVisible(false);
        });
        buttonPanel.add(button);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ustawienie odstępu 30 pikseli od górnej granicy okna
        rootPane.setBorder(BorderFactory.createEmptyBorder(30, 5, 5, 0));

        // Obsługa zdarzenia przycisku wyszukaj
        wyszukajButton.addActionListener(e -> {
            String idLekarzaText = idLekarzaTextField.getText();
            int idLekarza = Integer.parseInt(idLekarzaText);
            wyszukajWizyty(idLekarza);
        });
    }

    private void wyszukajWizyty(int idLekarza) {

        if(czyPosiadaSkierowanie(idPacjenta, idLekarza)) {
            // Wyczyszczenie panelu z przyciskami
            wizytyPanel.removeAll();
            wizytyPanel.revalidate();
            wizytyPanel.repaint();

            try {
                // Zapytanie SQL do pobrania dostępnych wizyt dla lekarza o podanym ID
                String sql = "SELECT Data, Godzina FROM dostepneWizyty WHERE idLekarza = " + idLekarza;
                ResultSet resultSet = polaczBaze(sql);

                // Przetwarzanie wyników zapytania i tworzenie przycisków dla każdej wizyty
                List<JButton> buttons = new ArrayList<>();
                while (resultSet.next()) {
                    Date data = resultSet.getDate("Data");
                    Time godzina = resultSet.getTime("Godzina");

                    String buttonText = "Data: " + data.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE) +
                            "  Godzina: " + godzina.toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME);

                    JButton button = new JButton(buttonText);
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    buttonPanel.setPreferredSize(new Dimension(250, 50));
                    buttonPanel.add(button);

                    button.addActionListener(e -> {

                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/przychodnia", "root", "");
                            String sql2 = "INSERT INTO wizyta (Data, Czas, idLekarza, idPacjenta) VALUES (?, ?, ?, ?)";

                            PreparedStatement statement = connection.prepareStatement(sql2);
                            statement.setDate(1, data);
                            statement.setTime(2, godzina);
                            statement.setInt(3, idLekarza);
                            statement.setInt(4, idPacjenta);

                            statement.executeUpdate(); // Wykonanie zapytania

                            String deleteSql = "DELETE FROM dostepneWizyty WHERE idLekarza = ? AND Data = ? AND Godzina = ?";
                            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                            deleteStatement.setInt(1, idLekarza);
                            deleteStatement.setDate(2, data);
                            deleteStatement.setTime(3, godzina);

                            deleteStatement.executeUpdate(); // Wykonanie zapytania usuwającego
                            deleteStatement.close();

                            statement.close();
                            connection.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        // Obsługa zdarzenia kliknięcia przycisku wizyty
                        JOptionPane.showMessageDialog(DostepneWizytyFrame.this, "Rezerwacja utworzona!");


                        WizytyPacjenta wP = new WizytyPacjenta(idPacjenta);
                        wP.setVisible(true);
                        setVisible(false);
                    });

                    buttons.add(button);
                    wizytyPanel.add(buttonPanel);

                }

                // Zamknięcie połączenia z bazą danych
                resultSet.close();

                // Aktualizacja panelu z przyciskami
                wizytyPanel.revalidate();
                wizytyPanel.repaint();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else{
            JOptionPane.showMessageDialog(DostepneWizytyFrame.this, "Nie znaleziono skierowania do specjalisty o podanym ID!");
        }
    }

    public static boolean czyPosiadaSkierowanie(int idPacjenta, int idLekarza) {
        try {
            // Ustanowienie połączenia z bazą danych
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/przychodnia", "root", "");

            String specjalizacjaSql = "SELECT specjalizacja FROM pracownik WHERE id = ?";
            PreparedStatement specjalizacjaStatement = connection.prepareStatement(specjalizacjaSql);
            specjalizacjaStatement.setInt(1, idLekarza);
            ResultSet specjalizacjaResultSet = specjalizacjaStatement.executeQuery();

            // Pobranie wyników zapytania specjalizacji lekarza
            specjalizacjaResultSet.next();
            String specjalizacja = specjalizacjaResultSet.getString("specjalizacja");
            if(!Objects.equals(specjalizacja, "Internista")) {

                // Zamknięcie zasobów związanych ze specjalizacją
                specjalizacjaResultSet.close();
                specjalizacjaStatement.close();

                // Zapytanie SQL do sprawdzenia, czy pacjent posiada skierowanie do lekarza o podanym ID
                String sql = "SELECT COUNT(*) FROM skierowanieSpecjalista WHERE idPacjenta = ? AND idDo = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, idPacjenta);
                statement.setInt(2, idLekarza);
                ResultSet resultSet = statement.executeQuery();

                // Pobranie wyników zapytania
                resultSet.next();
                int count = resultSet.getInt(1);

                // Zamknięcie połączenia z bazą danych
                resultSet.close();
                statement.close();
                connection.close();

                // Sprawdzenie, czy pacjent posiada skierowanie do lekarza o podanym ID
                return count > 0;
            }
            else return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}


