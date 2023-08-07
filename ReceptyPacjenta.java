import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class ReceptyPacjenta extends JFrame implements BazaDanychConnect {
    public ReceptyPacjenta(int idPacjenta) {
        super("Panel pacjenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        String[] columnNames = {"Lek", "Dawka", "Data Wygaśnięcia"};

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
            String query = "SELECT recepta.lek, recepta.mg AS dawka, recepta.dataWaznosci FROM recepta " +
                    "WHERE recepta.idPacjenta = " + idPacjenta;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String drug = resultSet.getString("lek");
                String amount = resultSet.getString("dawka");
                String date = resultSet.getString("dataWaznosci");
                Object[] rowData = {drug, amount, date};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }

        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(250);         //lek
        table.getColumnModel().getColumn(1).setPreferredWidth(100);         //dawka
        table.getColumnModel().getColumn(2).setPreferredWidth(250);         //data waznosci

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
