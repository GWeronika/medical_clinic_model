import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class WizytyPacjenta extends JFrame implements BazaDanychConnect {
    public WizytyPacjenta(int idPacjenta) {
        super("Panel pacjenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //panel na przyciski
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton topButton = new JButton(" + Umów wizytę ");

        topButton.addActionListener(e -> {
            DostepneWizytyFrame form = new DostepneWizytyFrame(idPacjenta);
            form.setVisible(true);
            setVisible(false);
        });


        topButton.setFocusPainted(false);
        topButton.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(topButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,-5));

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        String[] columnNames = {"ID Wizyty", "Data", "Godzina ","Lekarz"};

        DefaultTableModel model = new DefaultTableModel();

        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }

        JTable table = new JTable(model);

        //pobieranie danych do tabeli
        try {
            String query = "SELECT wizyta.id, wizyta.data, wizyta.czas, CONCAT(pracownik.imie, \" \", pracownik.nazwisko) AS lekarz FROM wizyta " +
                    "JOIN pracownik ON wizyta.idLekarza = pracownik.id WHERE wizyta.idPacjenta = " + idPacjenta;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String visID = resultSet.getString("id");
                String date = resultSet.getString("data");
                String time = resultSet.getString("czas");
                String doctor = resultSet.getString("lekarz");

                Object[] rowData = {visID,date,time, doctor};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }

        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(100);          //id Wizyty
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

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
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}