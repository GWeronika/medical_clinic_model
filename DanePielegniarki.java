import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DanePielegniarki extends JFrame implements BazaDanychConnect, ButtonInterface, LabelInterface {
    public DanePielegniarki(int idPielegniarki) {
        super("Panel pielęgniarki");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel(new BorderLayout());

        //tworzenie panelu centralnego
        JPanel centPanel = new JPanel();
        JLabel titleLabel = new JLabel("Dane pielegniarki");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);
        centPanel.setLayout(new BoxLayout(centPanel, BoxLayout.Y_AXIS));

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie napisu JLabel
        JLabel welcomeLabel = new JLabel("Panel pielegniarki " + idPielegniarki);
        welcomeLabel.setFont(new Font("Montserrat", Font.PLAIN, 32)); // Ustawienie rozmiaru napisu na 60 pikseli

        //tworzenie połączenia do bazy danych
        try {
            String query = "SELECT id, imie, nazwisko, specjalizacja FROM pracownik where pracownik.id = " + idPielegniarki;
            ResultSet resultSet = polaczBaze(query);

            //dodanie danych do modelu tabeli
            int id = 0;
            String name = "", surname = "", spec = "";
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("imie");
                surname = resultSet.getString("nazwisko");
                spec = resultSet.getString("specjalizacja");
            }

            GridBagConstraints comp = new GridBagConstraints();
            comp.gridx = 0;
            comp.gridy = 0;
            comp.anchor = GridBagConstraints.CENTER;
            comp.fill = GridBagConstraints.VERTICAL;
            centPanel.add(stworzPoleText("ID lekarza: " + id), comp);
            comp.gridy = 1;
            centPanel.add(stworzPoleText("Imię: " + name), comp);
            comp.gridy = 2;
            centPanel.add(stworzPoleText("Nazwisko: " + surname), comp);
            comp.gridy = 3;
            centPanel.add(stworzPoleText("Specjalizacja: " + spec), comp);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            FramePielegniarki pielegniarkaFrame = new FramePielegniarki(idPielegniarki);
            pielegniarkaFrame.setVisible(true);
            setVisible(false);
        });

        //dodanie panelu centralnego do głównego
        mainPanel.add(centPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        add(mainPanel);     //dodanie panelu głównego do okna

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
