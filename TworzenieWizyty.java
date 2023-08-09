import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class TworzenieWizyty extends JFrame implements BazaDanychConnect, BazaDanychModify, LabelInterface, ButtonInterface {
    public TworzenieWizyty(int idPielegniarki) {
        super("Panel pielegniarki");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        //tworzenie najbardziej głównego panelu
        JPanel superPanel = new JPanel(new BorderLayout());

        //tworzenie głównego panelu
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //tworzenie panelu centralnego
        JPanel centPanel = new JPanel();
        JLabel titleLabel = new JLabel("Nowa wizyta");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie panelu z etykietami, przyciskami i polami tekstowymi
        JPanel formPanel = new JPanel(new GridBagLayout());



        JLabel date = stworzPoleText("Data:  ");
        JLabel time = stworzPoleText("Czas trwania:  ");
        JLabel id = stworzPoleText("ID pacjenta:  ");
        JLabel docName = stworzPoleText("Specjalizacja:  ");

        JTextField dateField = new JTextField(15);
        JTextField timeField = new JTextField(15);
        JTextField idField = new JTextField(15);
        JTextField docField = new JTextField(15);


        //po lewej
        Insets insets = new Insets(0, 0, 5, 5);
        GridBagConstraints comp = ustawPrzycisk(insets, 0, 0);
        comp.anchor = GridBagConstraints.EAST;
        formPanel.add(date, comp);
        comp = ustawPrzycisk(insets, 0, 1);
        formPanel.add(time, comp);
        comp = ustawPrzycisk(insets, 0, 2);
        formPanel.add(id, comp);
        comp = ustawPrzycisk(insets, 0, 3);
        formPanel.add(docName, comp);

        //po prawej
        insets = new Insets(0, 0, 5, 0);
        comp.anchor = GridBagConstraints.WEST;
        comp = ustawPrzycisk(insets, 1, 0);
        formPanel.add(dateField, comp);
        comp = ustawPrzycisk(insets, 1, 1);
        formPanel.add(timeField, comp);
        comp = ustawPrzycisk(insets, 1, 2);
        formPanel.add(idField, comp);
        comp = ustawPrzycisk(insets, 1, 3);
        formPanel.add(docField, comp);


        //tworzenie przycisku do potwierdzenia utworzenia konta
        JButton confirmButton = new JButton("Utwórz wizytę");

        //reagowanie przycisku na wciśnięcie
        confirmButton.addActionListener(e -> {
            String date1 = dateField.getText();
            String time1 = timeField.getText();
            String id1 = idField.getText();
            String doc1 = docField.getText();

            ArrayList<String> idList = new ArrayList<>();               //lista przechowująca ID pacjentów
            ArrayList<String> specList = new ArrayList<>();             //lista przechowująca specjalizacje lekarzy
            String specID = "";

            //dodanie nowej wizyty do bazy danych
            try {
                ResultSet idSet = polaczBaze("select id from pacjent");

                //pobranie wartości z ResultSet i konwersja na String
                while (idSet.next()) {
                    String strID = idSet.getString("id");
                    idList.add(strID);
                }

                idSet = polaczBaze("select specjalizacja from pracownik");
                //pobranie wartości z ResultSet i konwersja na String
                while (idSet.next()) {
                    String specjalization = idSet.getString("specjalizacja");
                    specList.add(specjalization);
                }

                String querySelect = "SELECT id FROM pracownik WHERE id IN (SELECT MIN(id) FROM pracownik WHERE pracownik.specjalizacja = '" + doc1 + "')";
                ResultSet resultSet = polaczBaze(querySelect);
                if (resultSet.next())
                    specID = resultSet.getString("id");


                String query = "INSERT INTO wizyta (data, czas, idLekarza, idPacjenta) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStmt1 = modyfikujBaze(query);
                Date dateV = Date.valueOf(date1);
                preparedStmt1.setDate (1, dateV);
                preparedStmt1.setString (2, time1);
                preparedStmt1.setString (3, specID);
                preparedStmt1.setString (4, id1);
                preparedStmt1.execute();

            } catch (Exception exc) {
                System.out.println("Blad podczas dodania wizyty do bazy");
            }

            boolean foundID = false;
            //sprawdzanie poprawności danych logowania
            if (!time1.isEmpty() && !id1.isEmpty() && !date1.isEmpty() && !doc1.isEmpty()) {
                for (int i = 0; i < idList.toArray().length; i++) {
                    if (id1.equals(idList.get(i))) {          //jeśli istnieje takie id pacjenta
                        for(int j = 0; i < specList.size(); j++) {
                            if (doc1.equals(specList.get(j))) {           //jeśli w tabeli jest taka specjalizacja to możemy utworzyć konto
                                JOptionPane.showMessageDialog(TworzenieWizyty.this, "Utworzono skierowanie");
                                foundID = true;
                                WizytyPielegniarki WizytaFrame = new WizytyPielegniarki(idPielegniarki);
                                WizytaFrame.setVisible(true);
                                setVisible(false);
                                break;
                            }
                        }
                        break;          //jest tylko jeden pacjent o takim ID
                    }
                }
            }
            if(!foundID)
                JOptionPane.showMessageDialog(TworzenieWizyty.this, "Błędne dane!", "Błąd tworzenia swizyty", JOptionPane.ERROR_MESSAGE);
        });

        //ustawienie preferowanego rozmiaru dla przycisku potwierdzenia
        Dimension buttonSize = new Dimension(200, 30);
        confirmButton.setPreferredSize(buttonSize);


        insets = new Insets(10, 10, 60, 10);
        comp = ustawPrzycisk(insets, 0, 0);
        mainPanel.add(centPanel, comp);

        insets = new Insets(10, 10, 5, 10);
        comp = ustawPrzycisk(insets, 0, 1);
        mainPanel.add(formPanel, comp);

        comp = ustawPrzycisk(insets, 0, 2);
        mainPanel.add(confirmButton, comp);

        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            WizytyPielegniarki wizytaFrame = new WizytyPielegniarki(idPielegniarki);
            wizytaFrame.setVisible(true);
            setVisible(false);
        });

        //dodanie wszystkich paneli do panelu super
        superPanel.add(mainPanel, BorderLayout.CENTER);
        superPanel.add(bottomPanel, BorderLayout.PAGE_END);
        add(superPanel);     //dodanie panelu głównego do okna

        setVisible(true);

    }
    public JLabel stworzPoleText(String etykieta) {
        JLabel label = new JLabel(etykieta);
        label.setFont(new Font("Montserrat", Font.BOLD, 13));
        return label;
    }
    public JButton stworzPrzycisk(String textPrzycisku) {
        JButton button = new JButton(textPrzycisku);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));        //ustawienie rozmiarów przycisku
        return button;
    }
    public GridBagConstraints ustawPrzycisk(Insets insets, int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = insets;       //margines
        constraints.anchor = GridBagConstraints.CENTER;                         //wyśrodkowanie wzdłuż osi X
        return constraints;
    }
}
