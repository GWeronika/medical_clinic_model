import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DanePacjentow extends JFrame implements BazaDanychConnect {
    public DanePacjentow() {
        super("Dane pacjentów przychodni");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //panel główny
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        //panel na przycisk i przycisk
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteBut = new JButton("Usuń dane pacjenta");
        deleteBut.setFocusPainted(false);
        deleteBut.setPreferredSize(new Dimension(200, 30));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(5);
        idField.setPreferredSize(new Dimension(50,31));
        idLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        idField.setAlignmentY(Component.CENTER_ALIGNMENT);

        deleteBut.addActionListener(e -> {
            String id = idField.getText();

            ArrayList<String> idList = new ArrayList<>();      //lista na id pacjenta

            //usuwamy z bazy pacjenta o takim id
            try {
                ResultSet idSet = polaczBaze("select id from pacjent");
                //pobranie wartości z ResultSet i konwersja na String
                while (idSet.next()) {
                    String strID = idSet.getString("id");
                    idList.add(strID);
                }

                boolean foundID = false;
                //sprawdzanie poprawności danych logowania
                if (!id.isEmpty()) {
                    for (int i = 0; i < idList.toArray().length; i++) {
                        if (id.equals(idList.get(i))) {                 //jeśli istnieje takie id pacjenta
                            String sql = "DELETE FROM haslo_pacjent WHERE id=" + id;
                            polaczBaze(sql);

                            sql = "DELETE FROM pacjent_internista WHERE idPacjenta=" + id;
                            polaczBaze(sql);

                            sql = "DELETE FROM skierowanieBadanie WHERE idPacjenta=" + id;
                            polaczBaze(sql);

                            sql = "DELETE FROM badanie WHERE idPacjenta=" + id;
                            polaczBaze(sql);

                            sql = "DELETE FROM recepta WHERE idPacjenta=" + id;
                            polaczBaze(sql);

                            sql = "DELETE FROM skierowanieSpecjalista WHERE idPacjenta=" + id;
                            polaczBaze(sql);

                            sql = "DELETE FROM wizyta WHERE idPacjenta=" +id;
                            polaczBaze(sql);

                            sql = "DELETE FROM pacjent WHERE id=" + id;
                            polaczBaze(sql);

                            JOptionPane.showMessageDialog(DanePacjentow.this, "Usunięto pomyślnie!");
                            DanePacjentow dp = new DanePacjentow();
                            dp.setVisible(true);
                            setVisible(false);
                            break;          //jest tylko jeden pacjent o takim ID
                        }
                    }
                }
                if(!foundID)
                    throw new IllegalWrittenIDException();

            } catch (IllegalWrittenIDException il) {
                throw new IllegalWrittenIDException("Pacjent o taki ID nie istnieje");
            } catch (Exception ex) {
                System.out.println("Blad pobieranie danych z tabeli");
            }
        });

        topPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,-5));
        topPanel.add(idLabel);
        topPanel.add(idField);
        topPanel.add(deleteBut);

        String[] columnNames = {"ID", "Imię", "Nazwisko", "Data urodzenia", "PESEL", "Płeć", "Numer telefonu"};

        DefaultTableModel model = new DefaultTableModel();

        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }

        JTable table = new JTable(model);

        //pobieranie danych do tabeli
        try {
            String query = "SELECT id, imie, nazwisko, data_urodzenia, pesel, plec, nrTelefonu FROM pacjent";
            ResultSet resultSet = polaczBaze(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String dataUr = resultSet.getString("data_urodzenia");
                String PESEL = resultSet.getString("pesel");
                String plec = resultSet.getString("plec");
                String nrTelefonu = resultSet.getString("nrTelefonu");

                Object[] rowData = {id, imie, nazwisko, dataUr, PESEL, plec, nrTelefonu};
                model.addRow(rowData);
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(50);      //ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100);     //Imię
        table.getColumnModel().getColumn(2).setPreferredWidth(100);     //Nazwisko
        table.getColumnModel().getColumn(3).setPreferredWidth(100);     //dataUrodzenia
        table.getColumnModel().getColumn(4).setPreferredWidth(100);     //pesel
        table.getColumnModel().getColumn(5).setPreferredWidth(50);      //płeć
        table.getColumnModel().getColumn(6).setPreferredWidth(100);     //numer teelfonu

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

        //dodanie do panelu na dole
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0,-5,0,0));
        bottomPanel.add(goBackButton);

        //dodanie paneli do panelu głównego
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}