import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DodaniePracownikaPrzezAdmina extends JFrame implements LabelInterface, BazaDanychModify, ButtonInterface {
    public DodaniePracownikaPrzezAdmina() {
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
        JLabel titleLabel = new JLabel("Dodanie konta pracownika przychodni:");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie panelu z etykietami, przyciskami i polami tekstowymi
        JPanel formPanel = new JPanel(new GridBagLayout());

        JLabel name = stworzPoleText("Imię: ");
        JLabel surname = stworzPoleText("Nazwisko:  ");
        JLabel zawod = stworzPoleText("Specjalizacja:  ");

        JTextField nameField = new JTextField(15);
        JTextField surnameField = new JTextField(15);
        JTextField zawodField = new JTextField(15);


        //po lewej
        Insets insets = new Insets(0, 0, 5, 5);
        GridBagConstraints comp = ustawPrzycisk(insets, 0, 0);
        comp.anchor = GridBagConstraints.EAST;
        formPanel.add(name, comp);
        comp = ustawPrzycisk(insets, 0, 1);
        formPanel.add(surname, comp);
        comp = ustawPrzycisk(insets, 0, 2);
        formPanel.add(zawod, comp);

        //po prawej
        insets = new Insets(0, 0, 5, 0);
        comp.anchor = GridBagConstraints.WEST;
        comp = ustawPrzycisk(insets, 1, 0);
        formPanel.add(nameField, comp);
        comp = ustawPrzycisk(insets, 1, 1);
        formPanel.add(surnameField, comp);
        comp = ustawPrzycisk(insets, 1, 2);
        formPanel.add(zawodField, comp);


        //tworzenie przycisku do potwierdzenia utworzenia konta
        JButton confirmButton = stworzPrzycisk("Utwórz konto");

        //reagowanie przycisku na wciśnięcie
        confirmButton.addActionListener(e -> {
            String name1 = nameField.getText();
            String surname1 = surnameField.getText();
            String zawod1 = zawodField.getText();

            //sprawdzanie poprawności danych logowania
            if (!name1.isEmpty() && !surname1.isEmpty() && !zawod1.isEmpty()) {
                JOptionPane.showMessageDialog(DodaniePracownikaPrzezAdmina.this, "Konto zostało utworzone!");
                LoginPracownik LoginPracFrame = new LoginPracownik();
                LoginPracFrame.setVisible(true);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(DodaniePracownikaPrzezAdmina.this, "Błędne dane!", "Błąd tworzenia konta", JOptionPane.ERROR_MESSAGE);
            }

            //dodanie nowego pracownika do bazy danych
            try {
                String query = "INSERT INTO pracownik(imie, nazwisko, specjalizacja) VALUES (?, ?, ?)";
                PreparedStatement preparedStmt1 = modyfikujBaze(query);
                preparedStmt1.setString (1, name1);
                preparedStmt1.setString (2, surname1);
                preparedStmt1.setString(3, zawod1);
                preparedStmt1.execute();

                //dodanie jego hasla do tabeli haslo_pracownik
                query = "INSERT INTO haslo_pracownik (haslo) VALUES (?)";
                PreparedStatement preparedStmt2 = modyfikujBaze(query);
                preparedStmt2.setString (1, surname1); ///na razie jako haslo sie wstawia nazwisko
                preparedStmt2.execute();

            } catch (Exception exc) {
                System.out.println("Blad podczas dodania klienta do bazy");
            }

            ZespolLekarski zl = new ZespolLekarski();
            zl.setVisible(true);
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
            ZespolLekarski ZespolFrame = new ZespolLekarski();
            ZespolFrame.setVisible(true);
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
}