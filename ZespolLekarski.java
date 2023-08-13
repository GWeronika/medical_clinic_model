import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class ZespolLekarski extends JFrame implements BazaDanychConnect{
    public ZespolLekarski() {
        super("Zespoł pracowniczy");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //główny panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        //panel z przyciskiem i przycisk
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button = new JButton("+ Dodaj nowego pracownika");

        button.addActionListener(e -> {
            DodaniePracownikaPrzezAdmina dodPr = new DodaniePracownikaPrzezAdmina();
            dodPr.setVisible(true);
            setVisible(false);
        });

        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,-5));

        String[] columnNames = {"ID", "Imię", "Nazwisko", "Specjalizacja"};

        DefaultTableModel model = new DefaultTableModel();

        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }

        JTable table = new JTable(model);

        //pobieranie danych do tabeli
        try {
            String query = "SELECT id, imie, nazwisko, specjalizacja FROM pracownik";
            ResultSet resultSet = polaczBaze(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String specjalizacja = resultSet.getString("specjalizacja");

                Object[] rowData = {id, imie, nazwisko, specjalizacja};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(50);          // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100);         // Imię
        table.getColumnModel().getColumn(2).setPreferredWidth(100);         // Nazwisko
        table.getColumnModel().getColumn(3).setPreferredWidth(150);         // Specjalizacja

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            FrameAdmina adm = new FrameAdmina();
            adm.setVisible(true);
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
