import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LoginPracownik extends JFrame implements ButtonInterface, LabelInterface, BazaDanychConnect {
    public LoginPracownik() {
        super("Panel logowania");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie najbardziej głównego panelu
        JPanel superPanel = new JPanel(new BorderLayout());

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie panelu górnego z etykietą
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Zaloguj się do portalu pracownika:");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        topPanel.add(titleLabel);

        //tworzenie panelu z etykietami i polami tekstowymi
        JPanel formPanel = new JPanel(new GridBagLayout());
        JLabel usernameLabel = stworzPoleText("ID  ");
        JLabel passwordLabel = new JLabel("Hasło  ");
        passwordLabel.setFont(new Font("Montserrat", Font.BOLD, 13));
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        Insets insets = new Insets(0, 0, 5, 5);
        GridBagConstraints gbc = ustawPrzycisk(insets, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        gbc = ustawPrzycisk(insets, 0, 1);
        formPanel.add(passwordLabel, gbc);

        gbc = ustawPrzycisk(insets, 1, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(usernameField, gbc);

        gbc = ustawPrzycisk(insets, 1, 1);
        formPanel.add(passwordField, gbc);

        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            PageStart startFrame = new PageStart();
            startFrame.setVisible(true);
            setVisible(false);
        });

        //tworzenie przycisków
        JButton loginButton = stworzPrzycisk("Zaloguj");


        //tworzenie listy z id i haslami pracowników
        ArrayList<Integer> idList = new ArrayList<>();
        ArrayList<String> passList = new ArrayList<>();
        ArrayList<Boolean> specList = new ArrayList<>();        //specjalizacje lekarzy

        try {
            ResultSet idSet = polaczBaze("select id from haslo_pracownik");

            //pobranie wartości z ResultSet i konwersja na Integer
            while (idSet.next()) {
                Integer integerID = idSet.getInt("id");
                idList.add(integerID);
            }

            //to samo dla hasła
            idSet = polaczBaze("select haslo from haslo_pracownik");
            while (idSet.next())
                passList.add(idSet.getString("haslo"));

            //to samo dla rozróżnienia czy lekarz czy pielęgniarka
            idSet = polaczBaze("select if(specjalizacja != \"Pielęgniarka\", 1, 0) as spec from pracownik join haslo_pracownik on pracownik.id = haslo_pracownik.id");
            while (idSet.next())
                specList.add(idSet.getBoolean("spec"));

        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }


        //dodanie akcji do przycisków
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            Integer getID = Integer.parseInt(username);

            //sprawdzanie poprawności danych logowania
            FrameLekarza lekarzFrame;
            int i = 0; boolean foundAccount = false;
            while (i < idList.toArray().length) {
                if (getID.equals(idList.get(i)) && String.valueOf(password).equals(passList.get(i))) {          //jeśli i login i hasło z list będą się zgadzać to logowanie jest poprawne
                    foundAccount = true;
                    JOptionPane.showMessageDialog(LoginPracownik.this, "Zalogowano pomyślnie!");
                    if(specList.get(i)) {           //jeśli na liście specjalizacji boolean jest true to znaczy że to lekarz
                        lekarzFrame = new FrameLekarza(getID);
                        lekarzFrame.setVisible(true);
                    } else {
                        FramePielegniarki pielegniarkaFrame = new FramePielegniarki(getID);
                        pielegniarkaFrame.setVisible(true);
                    }
                    setVisible(false);
                    break;
                } else
                    i++;
            }
            if(!foundAccount)
                JOptionPane.showMessageDialog(LoginPracownik.this, "Błędne dane logowania!", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
        });

        //ustawienie preferowanego rozmiaru dla przycisków
        Dimension buttonSize = new Dimension(200, 30);
        loginButton.setPreferredSize(buttonSize);

        //tworzenie głównego panelu i dodawanie komponentów
        JPanel mainPanel = new JPanel(new GridBagLayout());
        insets = new Insets(10, 10, 60, 10);
        gbc = ustawPrzycisk(insets, 0, 0);
        mainPanel.add(topPanel, gbc);

        insets = new Insets(10, 10, 5, 10);
        gbc = ustawPrzycisk(insets, 0 , 1);
        mainPanel.add(formPanel, gbc);

        gbc = ustawPrzycisk(insets, 0 , 2);
        mainPanel.add(loginButton, gbc);

        //dodawanie głównego panelu do ramki
        superPanel.add(mainPanel, BorderLayout.CENTER);
        superPanel.add(bottomPanel, BorderLayout.PAGE_END);
        add(superPanel);     //dodanie panelu głównego do okna
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