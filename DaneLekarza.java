import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DaneLekarza extends JFrame implements LabelInterface, BazaDanychConnect {
    public DaneLekarza(int idLekarza) {
        super("Panel lekarza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel(new BorderLayout());

        //tworzenie panelu centralnego
        JPanel centPanel = new JPanel();
        JLabel titleLabel = new JLabel("Dane lekarza");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);
        centPanel.setLayout(new BoxLayout(centPanel, BoxLayout.Y_AXIS));

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie napisu JLabel
        JLabel welcomeLabel = new JLabel("Panel lekarza " + idLekarza);
        welcomeLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));       //ustawienie rozmiaru napisu

        //tworzenie połączenia do bazy danych
        try {
            ResultSet resultSet = polaczBaze("SELECT id, imie, nazwisko, specjalizacja FROM pracownik where pracownik.id = " + idLekarza);
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
            centPanel.add(stworzPoleText("      ID lekarza: " + id), comp);
            comp.gridy = 1;
            centPanel.add(stworzPoleText("      Imię: " + name), comp);
            comp.gridy = 2;
            centPanel.add(stworzPoleText("      Nazwisko: " + surname), comp);
            comp.gridy = 3;
            centPanel.add(stworzPoleText("      Specjalizacja: " + spec), comp);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            FrameLekarza lekarzFrame = new FrameLekarza(idLekarza);
            lekarzFrame.setVisible(true);
            setVisible(false);
        });

        //dodanie panelu centralnego do głównego
        mainPanel.add(centPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        add(mainPanel);     //dodanie panelu głównego do okna

        setVisible(true);
    }
    public JLabel stworzPoleText(String etykieta) {
        JLabel label = new JLabel(etykieta + "\n");
        label.setFont(new Font("Montserrat", Font.PLAIN, 22));          //ustawienie rozmiaru napisu na 60 pikseli
        label.setHorizontalAlignment(SwingConstants.RIGHT);                         //wyśrodkowanie tekstu do prawej strony
        return label;
    }
}
