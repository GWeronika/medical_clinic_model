import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class SkierowaniaLekarza extends JFrame implements BazaDanychConnect {
    public SkierowaniaLekarza(int idLekarza) {
        super("Panel lekarza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        //panel przycisku i przycisk
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button = new JButton("+ Dodaj nowe skierowanie");

        button.addActionListener(e -> {
            TworzenieSkierowania noweSkierowanieFrame = new TworzenieSkierowania(idLekarza);
            noweSkierowanieFrame.setVisible(true);
            setVisible(false);
        });

        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,-5));

        String[] columnNames = {"Imie", "Nazwisko", "Data urodzenia", "PESEL", "Płeć", "nr Telefonu", "Specjalista"};

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
            String query = "SELECT pacjent.imie, pacjent.nazwisko, data_urodzenia, pesel, plec, nrTelefonu, pracownik.specjalizacja AS lekarzSpecjalista " +
                    "FROM pacjent JOIN skierowaniespecjalista ON skierowaniespecjalista.idPacjenta = pacjent.id JOIN pracownik ON skierowaniespecjalista.idDo = pracownik.id " +
                    "WHERE skierowaniespecjalista.idOd = " + idLekarza;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String name = resultSet.getString("imie");
                String surname = resultSet.getString("nazwisko");
                String birthD = resultSet.getString("data_urodzenia");
                String pesel = resultSet.getString("pesel");
                String gender = resultSet.getString("plec");
                String tel = resultSet.getString("nrTelefonu");
                String spec = resultSet.getString("lekarzSpecjalista");

                Object[] rowData = {name, surname, birthD, pesel, gender, tel, spec};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }
        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);         //pesel
        table.getColumnModel().getColumn(4).setPreferredWidth(50);          //płeć
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);         //specjalista


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
