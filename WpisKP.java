import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WpisKP extends JFrame implements BazaDanychConnect, BazaDanychModify, LabelInterface, ButtonInterface {
    private boolean czyZrealizowane = false;
    public WpisKP(int idLekarza, String idWizyty) {
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
        JLabel titleLabel = new JLabel("Wpis do Karty Pacjenta");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie panelu z etykietami, przyciskami i polami tekstowymi
        JPanel formPanel = new JPanel(new GridBagLayout());


        JLabel diagnosis = stworzPoleText("Rozpoznanie:  ");
        JTextField diagnosisField = new JTextField(15);

        //po lewej
        Insets insets = new Insets(0, 0, 5, 5);
        GridBagConstraints comp = ustawPrzycisk(insets, 0, 0);
        comp.anchor = GridBagConstraints.EAST;
        formPanel.add(diagnosis, comp);

        //po prawej
        insets = new Insets(0, 0, 5, 0);
        comp.anchor = GridBagConstraints.WEST;
        comp = ustawPrzycisk(insets, 1, 0);
        formPanel.add(diagnosisField, comp);


        //tworzenie przycisku do potwierdzenia utworzenia konta
        JButton confirmButton = new JButton("Dodaj wpis");

        //reagowanie przycisku na wciśnięcie
        confirmButton.addActionListener(e -> {
            String diagnosis1 = diagnosisField.getText();

            //dodanie nowej wizyty do bazy danych
            try {
                //imię i nazwisko lekarza
                ResultSet resultSet = polaczBaze("SELECT CONCAT(imie, \" \", nazwisko) AS lekarz FROM pracownik WHERE id = " + idLekarza);
                String name = "";
                while (resultSet.next())
                    name = resultSet.getString("lekarz");

                //imię i nazwisko pacjenta
                resultSet = polaczBaze("SELECT CONCAT(pacjent.imie, \" \", pacjent.nazwisko) AS pacjent FROM pacjent " +
                        "JOIN wizyta ON wizyta.idPacjenta = pacjent.id WHERE wizyta.id = " + idWizyty);
                String nameP = "";
                while (resultSet.next())
                    nameP = resultSet.getString("pacjent");

                //aktualna data
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String nowDate = currentDate.format(format);

                String query = "INSERT INTO archiwum (Pracownik, Pacjent, Data, Opis) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStmt1 = modyfikujBaze(query);
                preparedStmt1.setString (1, name);
                preparedStmt1.setString (2, nameP);
                preparedStmt1.setString (3, nowDate);
                preparedStmt1.setString (4, diagnosis1);
                preparedStmt1.execute();
                System.out.println("5");

                //usuwanie wiersza z bazy wizyty
                String sql = "DELETE FROM wizyta WHERE id = " + idWizyty;
                PreparedStatement deleteStmtm = modyfikujBaze(sql);
                deleteStmtm.executeUpdate(sql);

                czyZrealizowane = true;
                WizytyLekarza wizytyFrame = new WizytyLekarza(idLekarza);
                wizytyFrame.setVisible(true);
                setVisible(false);
                System.out.println("7");

            } catch (Exception exc) {
                System.out.println("Blad podczas dodania wpisu do bazy");
            }
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
            WizytyLekarza wizytaFrame = new WizytyLekarza(idLekarza);
            wizytaFrame.setVisible(true);
            setVisible(false);
        });

        //dodanie wszystkich paneli do panelu super
        superPanel.add(mainPanel, BorderLayout.CENTER);
        superPanel.add(bottomPanel, BorderLayout.PAGE_END);
        add(superPanel);     //dodanie panelu głównego do okna

        setVisible(true);

    }
    public boolean isCzyZrealizowane() {            //informuje, że wprowadzony wpis był poprawny
        return czyZrealizowane;
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