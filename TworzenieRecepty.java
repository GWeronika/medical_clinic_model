import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class TworzenieRecepty extends JFrame implements LabelInterface, ButtonInterface, BazaDanychConnect, BazaDanychModify {
    public TworzenieRecepty(int idLekarza) {
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
        JLabel titleLabel = new JLabel("Nowa recepta");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie panelu z etykietami, przyciskami i polami tekstowymi
        JPanel formPanel = new JPanel(new GridBagLayout());


        JLabel idP = stworzPoleText("ID pacjenta:  ");
        JLabel drug = stworzPoleText("Lek:  ");
        JLabel amount = stworzPoleText("Dawka:  ");
        JLabel date = stworzPoleText("Data wygaśnięcia:  ");

        JTextField idField = new JTextField(15);
        JTextField drugField = new JTextField(15);
        JTextField amountField = new JTextField(15);
        JTextField dateField = new JTextField(15);


        //po lewej
        Insets insets = new Insets(0, 0, 5, 5);
        GridBagConstraints comp = ustawPrzycisk(insets, 0,0);
        comp.anchor = GridBagConstraints.EAST;
        formPanel.add(idP, comp);
        comp = ustawPrzycisk(insets, 0, 1);
        formPanel.add(drug, comp);
        comp = ustawPrzycisk(insets, 0, 2);
        formPanel.add(amount, comp);
        comp = ustawPrzycisk(insets, 0, 3);
        formPanel.add(date, comp);

        //po prawej
        insets = new Insets(0, 0, 5, 0);
        comp.anchor = GridBagConstraints.WEST;
        comp = ustawPrzycisk(insets, 1, 0);
        formPanel.add(idField, comp);
        comp = ustawPrzycisk(insets, 1, 1);
        formPanel.add(drugField, comp);
        comp = ustawPrzycisk(insets, 1, 2);
        formPanel.add(amountField, comp);
        comp = ustawPrzycisk(insets, 1, 3);
        formPanel.add(dateField, comp);


        //tworzenie przycisku do potwierdzenia utworzenia konta
        JButton confirmButton = stworzPrzycisk("Utwórz receptę");

        //reagowanie przycisku na wciśnięcie
        confirmButton.addActionListener(e -> {
            String id1 = idField.getText();
            String drug1 = drugField.getText();
            String amount1 = amountField.getText();
            String date1 = dateField.getText();


            ArrayList<String> idList = new ArrayList<>();          //lista przechowująca ID pacjentów

            //dodanie nowego skierowania do bazy danych
            try {
                ResultSet idSet = polaczBaze("select id from pacjent");

                //pobranie wartości z ResultSet i konwersja na String
                while (idSet.next()) {
                    String strID = idSet.getString("id");
                    idList.add(strID);
                }

                boolean foundID = false;
                //sprawdzanie poprawności danych logowania
                if (!id1.isEmpty() && !drug1.isEmpty() && !amount1.isEmpty() && !date1.isEmpty()) {
                    int i = 0;
                    while (i < idList.toArray().length) {
                        if (id1.equals(idList.get(i))) {          //jeśli istnieje takie id pacjenta to można utworzyć konto
                            JOptionPane.showMessageDialog(TworzenieRecepty.this, "Utworzono receptę");
                            foundID = true;
                            break;
                        } else
                            i++;
                    }
                }
                if(!foundID)
                    throw new IllegalWrittenIDException();


                String query = "INSERT INTO recepta (idLekarza, idPacjenta, lek, mg, dataWaznosci) VALUES (" + idLekarza + ", ?, ?, ?, ?)";
                PreparedStatement preparedStmt1 = modyfikujBaze(query);
                preparedStmt1.setString (1, id1);
                preparedStmt1.setString (2, drug1);
                preparedStmt1.setString (3, amount1);

                //konwersja tekstu na Date
                Date dateW = Date.valueOf(date1);
                preparedStmt1.setDate (4, dateW);
                preparedStmt1.execute();

            } catch (IllegalWrittenIDException il) {
                throw new IllegalWrittenIDException("Pacjent o takim ID nie istnieje");
            } catch (Exception exc) {
                System.out.println("Blad podczas dodania recepty do bazy");
            }

            ReceptyLekarza rL = new ReceptyLekarza(idLekarza);
            rL.setVisible(true);
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
            SkierowanieLekarzaBadania SkierowanieFrame = new SkierowanieLekarzaBadania(idLekarza);
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
