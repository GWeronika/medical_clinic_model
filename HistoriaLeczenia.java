import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HistoriaLeczenia extends JFrame implements BazaDanychConnect {
    public HistoriaLeczenia(int idPacjenta) {
        super("Panel pacjenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        String[] columnNames = {"Specjalista", "Data wizyty", "Opis"};

        //tworzenie pustego modelu
        DefaultTableModel model = new DefaultTableModel();
        //ustawienie nagłówków kolumn
        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }
        //ustawienie danych w tabeli
        JTable table = new JTable(model);

        //pobieranie danych do tabeli
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/przychodnia", "root", "");

            String imieP =  "Select concat(imie,' ',nazwisko) as i from pacjent WHERE id = " + idPacjenta;
            PreparedStatement st = connection.prepareStatement(imieP);
            ResultSet specjalizacjaResultSet = st.executeQuery();

            // Pobranie wyników zapytania specjalizacji lekarza
            specjalizacjaResultSet.next();
            String pac = specjalizacjaResultSet.getString("i");

            String query = "SELECT archiwum.Pracownik, archiwum.Data, archiwum.Opis FROM archiwum " +
                    "WHERE Pacjent = '" + pac+"'";
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String idPrac = resultSet.getString("Pracownik");
                String date = resultSet.getString("Data");
                String opis = resultSet.getString("Opis");
                Object[] rowData = {idPrac, date, opis};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }

        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(250);         //lekarz
        table.getColumnModel().getColumn(1).setPreferredWidth(100);         //data
        table.getColumnModel().getColumn(2).setPreferredWidth(250);         //opis

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            FramePacjenta pacFrame = new FramePacjenta(idPacjenta);
            pacFrame.setVisible(true);
            setVisible(false);
        });
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0,-5,0,0));

        //dodanie paneli do panelu głównego
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
