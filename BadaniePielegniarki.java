import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BadaniePielegniarki extends JFrame implements BazaDanychConnect, BazaDanychModify{
    private static final ArrayList<Integer> usunieteWiersze = new ArrayList<>();
    public BadaniePielegniarki(int idPielegniarki) {
        super("Panel pielegniarki");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        //panel na przyciski
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button = new JButton("Zrealizuj badanie");

        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,-5));


        String[] columnNames = {"ID", "Data Badania", "Czas Badania", "ID Pielęgniarki", "ID Pacjenta", "ID Skierowania"};

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
            String query = "SELECT * FROM badanie WHERE badanie.idPielengniarki = " + idPielegniarki;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String date = resultSet.getString("data");
                String time = resultSet.getString("czas");
                String nurse = resultSet.getString("idPielengniarki");
                String patient = resultSet.getString("idPacjenta");
                String referral = resultSet.getString("idSkierownania");

                Object[] rowData = {id, date, time, nurse, patient, referral};
                model.addRow(rowData);
            }

            //usunięcie zaznaczonych wierszy z modelu tabeli
            for (int i = usunieteWiersze.size() - 1; i >= 0; i--) {
                int rowIndex = usunieteWiersze.get(i);
                if (rowIndex >= 0 && rowIndex < model.getRowCount()) {
                    model.removeRow(rowIndex);
                }
            }

            //reakcja na wciśnięcie przycisku zrealizowania wizyty => wiersz jest usuwany z tabeli badanie, ale nie z bazy danych
            button.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                String idBadania = (String) model.getValueAt(selectedRow, 0); //pobranie ID badania zaznaczonego wiersza

                String nazwaBadania = "";
                try {
                    String query2 = "SELECT skierowaniebadanie.nazwa_badania FROM skierowaniebadanie JOIN badanie ON badanie.idSkierownania = skierowaniebadanie.id WHERE badanie.id = " + idBadania;
                    ResultSet newSet = polaczBaze(query2);

                    if (newSet.next()) {
                        nazwaBadania = newSet.getString("nazwa_badania");
                    }

                    newSet.close();
                } catch (Exception d) {
                    System.out.println("Blad pobieranie danych z tabeli");
                }

                //dodanie indeksów usuniętych wierszy do listy
                if (!usunieteWiersze.contains(selectedRow))
                    usunieteWiersze.add(selectedRow);

                //usunięcie zaznaczonych wierszy z tabeli, ale nie z bazy danych
                model.removeRow(selectedRow);

                JOptionPane.showMessageDialog(BadaniePielegniarki.this, "Badanie " + nazwaBadania + " zrealizowane!");
            });

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }
        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(50);          //id
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);          //idPielęgniarki
        table.getColumnModel().getColumn(4).setPreferredWidth(50);             //idPacjenta
        table.getColumnModel().getColumn(5).setPreferredWidth(50);          //idSkierowania


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            FramePielegniarki pielFrame = new FramePielegniarki(idPielegniarki);
            pielFrame.setVisible(true);
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
