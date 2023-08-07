import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class BadaniaPacjenta extends JFrame implements BazaDanychConnect, BazaDanychModify{
    public BadaniaPacjenta(int idPacjenta) {
        super("Panel pielegniarki");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        String[] columnNames = {"ID", "Data Badania", "Czas Badania", "Pielegniarka", "ID Skierowania"};

        //tworzenie pustego modelu
        DefaultTableModel model = new DefaultTableModel();

        //ustawienie kolumny na podstawie columnNames
        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }
        //ustawienie modelu w tabeli
        JTable table = new JTable(model);

        //pobieranie danych do tabeli
        try {
            String query = "SELECT badanie.id, badanie.data, badanie.czas, CONCAT(pracownik.imie, \" \", pracownik.nazwisko) AS Pielegniarka FROM badanie"+
                    " JOIN pracownik on badanie.idPielengniarki = pracownik.id WHERE badanie.idPacjenta = " + idPacjenta;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String date = resultSet.getString("data");
                String time = resultSet.getString("czas");
                String nurse = resultSet.getString("Pielegniarka");
                String referral = resultSet.getString("idSkierownania");

                Object[] rowData = {id, date, time, nurse, referral};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }
        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(75);          //id
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);          //Pielęgniarki
        table.getColumnModel().getColumn(4).setPreferredWidth(75);          //idSkierowania


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
