import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class SkierowaniaPacjenta extends JFrame implements BazaDanychConnect {
    public SkierowaniaPacjenta(int idPacjenta) {
        super("Panel pacjenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        String[] columnNames = { "Wystawił", "Specjalista", "Specjalizacja", " "};

        //pusty model
        DefaultTableModel model = new DefaultTableModel();

        //ustawienie nagłówków tabeli columnNames
        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }
        //ustawienie danych w tabeli
        JTable table = new JTable(model);

        //pobieranie danych do tabeli
        try {
            String query = "SELECT CONCAT(p1.imie, ' ', p1.nazwisko) as LekarzOd, CONCAT(p2.imie, ' ', p2.nazwisko) AS LekarzDo, p2.Specjalizacja FROM skierowanieSpecjalista s " +
                    "JOIN pracownik p1 ON s.idOd = p1.id JOIN pracownik p2 ON s.idDo = p2.id WHERE s.idPacjenta = " + idPacjenta;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String odKogo = resultSet.getString("LekarzOd");
                String doKogo = resultSet.getString("LekarzDo");
                String spec = resultSet.getString("Specjalizacja");

                Object[] rowData = {odKogo, doKogo, spec};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }
        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

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
