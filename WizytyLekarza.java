import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class WizytyLekarza extends JFrame implements BazaDanychConnect {
    private static final ArrayList<Integer> usunieteWiersze = new ArrayList<>();
    public WizytyLekarza(int idLekarza) {
        super("Panel lekarza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        //panel na przyciski
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button = new JButton("Zrealizuj wizytę");

        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,-5));


        String[] columnNames = {"ID Wizyty", "Imie", "Nazwisko", "PESEL", "Płeć", "Data wizyty", "Lekarz"};

        DefaultTableModel model = new DefaultTableModel();

        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }

        JTable table = new JTable(model);


        //pobieranie danych do tabeli
        try {
            String query = "SELECT wizyta.id, pacjent.imie, pacjent.nazwisko, pesel, plec, wizyta.data, CONCAT(pracownik.imie, \" \", pracownik.nazwisko) AS lekarz FROM pacjent " +
                    "JOIN wizyta ON wizyta.idPacjenta = pacjent.id JOIN pracownik ON wizyta.idLekarza = pracownik.id WHERE wizyta.idLekarza = " + idLekarza;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            while (resultSet.next()) {
                String visID = resultSet.getString("id");
                String name = resultSet.getString("imie");
                String surname = resultSet.getString("nazwisko");
                String pesel = resultSet.getString("pesel");
                String gender = resultSet.getString("plec");
                String date = resultSet.getString("data");
                String doctor = resultSet.getString("lekarz");

                Object[] rowData = {visID, name, surname, pesel, gender, date, doctor};
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
                if(selectedRow == -1)
                    throw new NotSelectedRowException("Nie wybrano żadnego wiersza");

                String idWizyty = (String) model.getValueAt(selectedRow, 0); //pobranie ID badania zaznaczonego wiersza

                String dataWizyty = "";
                try {
                    String query2 = "SELECT data FROM wizyta WHERE id = " + idWizyty;
                    ResultSet newSet = polaczBaze(query2);

                    if (newSet.next()) {
                        dataWizyty = newSet.getString("data");
                    }

                    newSet.close();
                } catch (Exception d) {
                    System.out.println("Blad pobieranie danych z tabeli");
                }

                //usunięcie zaznaczonych wierszy z tabeli, ale nie z bazy danych
                model.removeRow(selectedRow);

                WpisKP wpisKP = new WpisKP(idLekarza, idWizyty);          //dodanie wpisu do wizyty
                wpisKP.setVisible(true);
                setVisible(false);
                if(wpisKP.isCzyZrealizowane()) {            //jeśli lekarz rzeczywiście zrealizował wizytę, a nie wyszedł np przyciskiem powrotu to komunikat
                    JOptionPane.showMessageDialog(WizytyLekarza.this, "Wizyta " + dataWizyty + " zrealizowane!");
                    //dodanie indeksów usuniętych wierszy do listy
                    if (!usunieteWiersze.contains(selectedRow))
                        usunieteWiersze.add(selectedRow);
                }
            });

            resultSet.close();
        } catch (NotSelectedRowException ns) {
            throw new NotSelectedRowException("Nie wybrano żadnego wiersza");
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }

        //ustawienie preferowanego rozmiaru kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(50);          //id Wizyty
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);         //pesel
        table.getColumnModel().getColumn(4).setPreferredWidth(50);         //płeć
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);


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