import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class ReceptyLekarza extends JFrame implements BazaDanychConnect {
    public ReceptyLekarza(int idLekarza) {
        super("Panel lekarza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        //tworzenie panelu na przycisk powrotu
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button = new JButton("+ Dodaj nową receptę");

        button.addActionListener(e -> {
            TworzenieRecepty nowaReceptaFrame = new TworzenieRecepty(idLekarza);
            nowaReceptaFrame.setVisible(true);
            setVisible(false);
        });

        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,-5));

        String[] columnNames = {"Imie", "Nazwisko", "PESEL", "Płeć", "Lek", "Dawka", "Data Wygaśnięcia"};

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
            String query = "SELECT pacjent.imie, pacjent.nazwisko, pesel, plec, recepta.lek, recepta.mg AS dawka, recepta.dataWaznosci FROM pacjent " +
                    "JOIN recepta ON recepta.idPacjenta = pacjent.id WHERE recepta.idLekarza = " + idLekarza;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String name = resultSet.getString("imie");
                String surname = resultSet.getString("nazwisko");
                String pesel = resultSet.getString("pesel");
                String gender = resultSet.getString("plec");
                String drug = resultSet.getString("lek");
                String amount = resultSet.getString("dawka");
                String date = resultSet.getString("dataWaznosci");
                Object[] rowData = {name, surname, pesel, gender, drug, amount, date};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }

        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);         //pesel
        table.getColumnModel().getColumn(3).setPreferredWidth(50);         //płeć
        table.getColumnModel().getColumn(4).setPreferredWidth(150);         //lek
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            FrameLekarza lekarzFrame = new FrameLekarza(idLekarza);
            lekarzFrame.setVisible(true);
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
