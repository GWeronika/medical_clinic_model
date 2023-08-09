import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class TworzenieSkierowania extends JFrame implements LabelInterface, ButtonInterface, BazaDanychConnect, BazaDanychModify {
    public TworzenieSkierowania(int idLekarza) {
        super("Panel lekarza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        //tworzenie najbardziej głównego panelu
        JPanel superPanel = new JPanel(new BorderLayout());

        //tworzenie głównego panelu
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //tworzenie panelu centralnego
        JPanel centPanel = new JPanel();
        JLabel titleLabel = new JLabel("Nowe skierowanie");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie panelu z etykietami, przyciskami i polami tekstowymi
        JPanel formPanel = new JPanel(new GridBagLayout());


        JLabel name = stworzPoleText("Wymagana specjalizacja lekarza:  ");
        JLabel patientId = stworzPoleText("ID pacjenta:  ");

        JTextField nameField = new JTextField(15);
        JTextField patientIdField = new JTextField(15);


        //po lewej
        Insets insets = new Insets(0, 0, 5, 5);
        GridBagConstraints comp = ustawPrzycisk(insets, 0, 0);
        comp.anchor = GridBagConstraints.EAST;
        formPanel.add(name, comp);

        comp = ustawPrzycisk(insets, 0, 1);
        formPanel.add(patientId, comp);

        //po prawej
        insets = new Insets(0, 0, 5, 0);
        comp.anchor = GridBagConstraints.WEST;
        comp = ustawPrzycisk(insets, 1, 0);
        formPanel.add(nameField, comp);
        comp = ustawPrzycisk(insets, 1, 1);
        formPanel.add(patientIdField, comp);


        //tworzenie przycisku do potwierdzenia utworzenia konta
        JButton confirmButton = stworzPrzycisk("Utwórz skierowanie");

        //reagowanie przycisku na wciśnięcie
        confirmButton.addActionListener(e -> {
            String spec = nameField.getText();
            String patientId1 = patientIdField.getText();

            ArrayList<String> idList = new ArrayList<>();      //lista na id pacjenta
            ArrayList<String> idListSpec = new ArrayList<>();           //lista na dostępne specjalizacje
            String specID = "";

            //dodanie nowego skierowania do bazy danych
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
                    idListSpec.add(specjalization);
                }


                String querySelect = "SELECT id FROM pracownik WHERE id IN (SELECT MIN(id) FROM pracownik WHERE pracownik.specjalizacja = '" + spec + "')";
                ResultSet resultSet = polaczBaze(querySelect);
                if (resultSet.next())
                    specID = resultSet.getString("id");

                boolean foundID = false;
                //sprawdzanie poprawności danych logowania
                if (!spec.isEmpty() && !patientId1.isEmpty()) {
                    for (int i = 0; i < idList.toArray().length; i++) {
                        if (patientId1.equals(idList.get(i))) {                 //jeśli istnieje takie id pacjenta
                            for(int j = 0; i < idListSpec.size(); j++) {
                                if (spec.equals(idListSpec.get(j))) {           //jeśli w tabeli jest taka specjalizacja to możemy utworzyć konto
                                    JOptionPane.showMessageDialog(TworzenieSkierowania.this, "Utworzono skierowanie");
                                    foundID = true;
                                    break;
                                }
                            }
                            break;          //jest tylko jeden pacjent o takim ID
                        }
                    }
                }
                if(!foundID)
                    throw new IllegalWrittenIDException();


                String query = "INSERT INTO skierowaniespecjalista (idOd, idDo, idPacjenta) VALUES (" + idLekarza + ", ?, ?)";
                PreparedStatement preparedStmt1 = modyfikujBaze(query);
                preparedStmt1.setString (1, specID);
                preparedStmt1.setString (2, patientId1);
                preparedStmt1.execute();

            } catch (IllegalWrittenIDException il) {
                throw new IllegalWrittenIDException("Nieprawidłowe ID Pacjenta lub specjalizacja lekarza");
            } catch (Exception exc) {
                System.out.println("Blad podczas dodania skierowania do bazy");
            }

            SkierowaniaLekarza skL2 = new SkierowaniaLekarza(idLekarza);
            skL2.setVisible(true);
            setVisible(false);
        });

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
            SkierowaniaLekarza SkierowanieFrame = new SkierowaniaLekarza(idLekarza);
            SkierowanieFrame.setVisible(true);
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
        //ustawienie preferowanego rozmiaru dla przycisku potwierdzenia
        Dimension buttonSize = new Dimension(200, 30);
        button.setPreferredSize(buttonSize);
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
