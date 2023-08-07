import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LoginPacjent extends JFrame implements LabelInterface, ButtonInterface, BazaDanychConnect {
    JPanel mainPanel;
    public LoginPacjent() {
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
        JLabel titleLabel = new JLabel("Zaloguj się do portalu pacjenta");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        topPanel.add(titleLabel);

        //tworzenie panelu z etykietami i polami tekstowymi
        JPanel formPanel = new JPanel(new GridBagLayout());
        JLabel usernameLabel = stworzPoleText("ID:  ");
        JLabel passwordLabel = new JLabel("Hasło:  ");
        passwordLabel.setFont(new Font("Montserrat", Font.BOLD, 13));
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        Insets insets = new Insets(0, 0, 5, 5);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc = ustawPrzycisk(insets, 0, 0);
        formPanel.add(usernameLabel, gbc);

        gbc = ustawPrzycisk(insets, 0, 1);
        formPanel.add(passwordLabel, gbc);

        insets = new Insets(0, 0, 5, 0);
        gbc = ustawPrzycisk(insets, 1, 0);
        gbc.anchor = GridBagConstraints.WEST;
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
        JButton createAccountButton = stworzPrzycisk("Utwórz konto");

        //tworzenie listy z id i haslami pracowników
        ArrayList<Integer> idList = new ArrayList<>();
        ArrayList<String> passList = new ArrayList<>();

        try {
            ResultSet idSet = polaczBaze("select id from haslo_pacjent");

            //pobranie wartości z ResultSet i konwersja na Integer
            while (idSet.next()) {
                idList.add(idSet.getInt("id"));
            }

            //to samo dla hasła
            idSet = polaczBaze("select haslo from haslo_pacjent");
            while (idSet.next())
                passList.add(idSet.getString("haslo"));

        } catch (Exception e) {
            System.out.println("Blad pobieranie danych z tabeli");
        }


        //dodanie akcji do przycisków
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            Integer getID = Integer.parseInt(username);

            //sprawdzanie poprawności danych logowania
            int i = 0;
            boolean foundAccount = false;
            while (i < idList.toArray().length) {
                if (getID.equals(idList.get(i)) && String.valueOf(password).equals(passList.get(i))) {          //jeśli i login i hasło z list będą się zgadzać to logowanie jest poprawne
                    foundAccount = true;
                    FramePacjenta frP = new FramePacjenta(getID);
                    frP.setVisible(true);
                    setVisible(false);
                    break;
                } else
                    i++;
            }
            if(!foundAccount)
                JOptionPane.showMessageDialog(LoginPacjent.this, "Błędne dane logowania!", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
        });

        createAccountButton.addActionListener(e -> {
            TworzenieKonta noweKontoFrame = new TworzenieKonta();
            noweKontoFrame.setVisible(true);
            setVisible(false);
        });

        //ustawienie preferowanego rozmiaru dla przycisków
        Dimension buttonSize = new Dimension(200, 30);
        loginButton.setPreferredSize(buttonSize);
        createAccountButton.setPreferredSize(buttonSize);

        //tworzenie głównego panelu i dodawanie komponentów
        mainPanel = new JPanel(new GridBagLayout());
        insets = new Insets(10, 10, 60, 10);
        gbc = ustawPrzycisk(insets, 0, 0);
        mainPanel.add(topPanel, gbc);

        insets = new Insets(10, 10, 5, 10);
        gbc = ustawPrzycisk(insets, 0, 1);
        mainPanel.add(formPanel, gbc);

        gbc = ustawPrzycisk(insets, 0, 2);
        mainPanel.add(loginButton, gbc);

        gbc = ustawPrzycisk(insets, 0, 3);
        mainPanel.add(createAccountButton, gbc);

        //dodanie głównego panelu do ramki
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