import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TworzenieKonta extends JFrame implements LabelInterface, ButtonInterface, BazaDanychModify {
    private final ButtonGroup group;
    public TworzenieKonta() {
        super("Tworzenie konta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        //tworzenie najbardziej głównego panelu
        JPanel superPanel = new JPanel(new BorderLayout());

        //tworzenie głównego panelu
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //tworzenie panelu centralnego
        JPanel centPanel = new JPanel();
        JLabel titleLabel = new JLabel("Zarejestruj się");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie panelu z etykietami, przyciskami i polami tekstowymi
        JPanel formPanel = new JPanel(new GridBagLayout());


        JLabel name = stworzPoleText("Imię: ");
        JLabel surname = stworzPoleText("Nazwisko:  ");
        JLabel dateOfB = stworzPoleText("Data urodzenia:  ");
        JLabel ssn = stworzPoleText("Pesel:  ");
        JLabel gender = stworzPoleText("Płeć:  ");
        JLabel phoneNum = stworzPoleText("Nr telefonu:  ");
        JLabel pass = stworzPoleText("Hasło:  ");

        JTextField nameField = new JTextField(15);
        JTextField surnameField = new JTextField(15);
        JTextField dateField = new JTextField(15);
        JTextField ssnField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);


        //w środku tworzenie podpanelu, w którym umieszczony zostaną trzy przyciski w siatce
        group = new ButtonGroup();
        JPanel buttonPanel = new JPanel();
        JRadioButton buttonW = new JRadioButton("K", true);
        JRadioButton buttonM = new JRadioButton("M", false);
        JRadioButton buttonI = new JRadioButton("Inny", false);

        panelWybor(buttonW, buttonPanel);
        panelWybor(buttonM, buttonPanel);
        panelWybor(buttonI, buttonPanel);

        //po lewej
        Insets insets = new Insets(0, 0, 5, 5);
        GridBagConstraints comp = new GridBagConstraints();
        comp.anchor = GridBagConstraints.EAST;
        comp = ustawPrzycisk(insets, 0, 0);
        formPanel.add(name, comp);
        comp = ustawPrzycisk(insets, 0, 1);
        formPanel.add(surname, comp);
        comp = ustawPrzycisk(insets, 0, 2);
        formPanel.add(dateOfB, comp);
        comp = ustawPrzycisk(insets, 0, 3);
        formPanel.add(ssn, comp);
        comp = ustawPrzycisk(insets, 0, 4);
        formPanel.add(gender, comp);
        comp = ustawPrzycisk(insets, 0, 5);
        formPanel.add(phoneNum, comp);
        comp = ustawPrzycisk(insets, 0, 6);
        formPanel.add(pass, comp);

        //po prawej
        insets = new Insets(0, 0, 5, 0);
        comp.anchor = GridBagConstraints.WEST;
        comp = ustawPrzycisk(insets, 1, 0);
        formPanel.add(nameField, comp);
        comp = ustawPrzycisk(insets, 1, 1);
        formPanel.add(surnameField, comp);
        comp = ustawPrzycisk(insets, 1, 2);
        formPanel.add(dateField, comp);
        comp = ustawPrzycisk(insets, 1, 3);
        formPanel.add(ssnField, comp);
        comp = ustawPrzycisk(insets, 1, 4);
        formPanel.add(buttonPanel, comp);
        comp = ustawPrzycisk(insets, 1, 5);
        formPanel.add(phoneField, comp);
        comp = ustawPrzycisk(insets, 1, 6);
        formPanel.add(passField, comp);


        //tworzenie przycisku do potwierdzenia utworzenia konta
        JButton confirmButton = stworzPrzycisk("Utwórz konto");

        //reagowanie przycisku na wciśnięcie
        confirmButton.addActionListener(e -> {
            String name1 = nameField.getText();
            String surname1 = surnameField.getText();
            String date = dateField.getText();
            String pesel = ssnField.getText();
            String phone = phoneField.getText();
            char[] password = passField.getPassword();
            String passStr = new String(password);

            //sprawdzanie poprawności danych logowania
            if (!name1.isEmpty() && !surname1.isEmpty() && !date.isEmpty() && !phone.isEmpty() && password.length > 0) {
                if(isPasswordStrong(password)) {        //weryfikacja mocy hasła
                    JOptionPane.showMessageDialog(TworzenieKonta.this, "Konto zostało utworzone!");
                    LoginPacjent LoginPacjentFrame = new LoginPacjent();
                    LoginPacjentFrame.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(TworzenieKonta.this, "Hasło powinno się składać z wielkich i małych liter, a także cyfr i znaków specjalnych",
                            "Blad tworzenia konta", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(TworzenieKonta.this, "Błędne dane!", "Błąd tworzenia konta", JOptionPane.ERROR_MESSAGE);
            }
            //pobieranie plci
            String plec = "";
            if (buttonW.isSelected()) {
                plec = "k";
            } else if (buttonM.isSelected()) {
                plec = "m";
            } else if (buttonI.isSelected()) {
                plec = "inny";
            }

            //dodanie nowego klienta do bazy danych
            try {
                String query = "INSERT INTO pacjent (imie, nazwisko, data_urodzenia, pesel, plec, nrTelefonu ) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt1 = modyfikujBaze(query);
                preparedStmt1.setString (1, name1);
                preparedStmt1.setString (2, surname1);
                //konwersja tekstu na Date
                Date dateUr = Date.valueOf(date);
                preparedStmt1.setDate (3, dateUr);
                preparedStmt1.setString(4, pesel);
                preparedStmt1.setString(5, plec);
                preparedStmt1.setString(6, phone);
                preparedStmt1.execute();

                //dodanie jego haslo do tabeli haslo_pacjent
                query = "INSERT INTO haslo_pacjent (haslo) VALUES (?)";
                PreparedStatement preparedStmt2 = modyfikujBaze(query);
                preparedStmt2.setString (1, passStr);
                preparedStmt2.execute();

            } catch (Exception exc) {
                System.out.println("Blad podczas dodania klienta do bazy");
            }
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
            LoginPacjent LoginPacjentFrame = new LoginPacjent();
            LoginPacjentFrame.setVisible(true);
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
        //ustawienie preferowanego rozmiaru dla przycisku
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
    private void panelWybor(JRadioButton button, JPanel panel) {        //dodaje radio buttony do panelu grupowego i odpowiedniego JPanelu
        group.add(button);
        panel.add(button);
    }
    private boolean isPasswordStrong(char[] password) {         //funkcja sprawdza czy hasło zawiera: wielkie/małe litery, cyfry i znaki specjalne
        String passwordString = new String(password);

        boolean hasUppercase = passwordString.matches(".*[A-Z].*");     //sprawdzanie występowania wielkich liter
        boolean hasLowercase = passwordString.matches(".*[a-z].*");     //sprawdzanie małych liter
        boolean hasDigit = passwordString.matches(".*\\d.*");           //cyfr
        boolean hasSpecialChar = passwordString.matches(".*[^a-zA-Z0-9].*");        //znaków specjalnych

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;             //sprawdzanie warunków i zwracanie wyniku
    }
}