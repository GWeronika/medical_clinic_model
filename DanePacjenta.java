import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DanePacjenta extends JFrame implements LabelInterface, BazaDanychConnect {
    public DanePacjenta(int idPacjenta) {
        super("Panel pacjenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel(new BorderLayout());

        //tworzenie panelu centralnego
        JPanel centPanel = new JPanel();
        JLabel titleLabel = new JLabel("            ");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));
        centPanel.add(titleLabel);
        centPanel.setLayout(new BoxLayout(centPanel, BoxLayout.Y_AXIS));

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie napisu JLabel
        JLabel welcomeLabel = new JLabel("Panel pacjenta " + idPacjenta);
        welcomeLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));       //ustawienie rozmiaru napisu

        //tworzenie połączenia do bazy danych
        try {
            ResultSet resultSet = polaczBaze("SELECT id, imie, nazwisko, data_urodzenia, pesel, nrTelefonu  FROM pacjent where pacjent.id = " + idPacjenta);
            //dodanie danych do modelu tabeli
            int id = 0;
            String name = "", surname = "", data = "", pesel = "", tel = "";
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("imie");
                surname = resultSet.getString("nazwisko");
                data = resultSet.getDate("data_urodzenia").toString();
                pesel = resultSet.getString("pesel");
                tel = resultSet.getString("nrTelefonu");
            }

            GridBagConstraints comp = new GridBagConstraints();
            comp.gridx = 0;
            comp.gridy = 0;
            comp.anchor = GridBagConstraints.CENTER;
            comp.fill = GridBagConstraints.VERTICAL;
            centPanel.add(stworzPoleText("      ID pacjenta: " + id), comp);
            comp.gridy = 1;
            centPanel.add(stworzPoleText("      Imię: " + name), comp);
            comp.gridy = 2;
            centPanel.add(stworzPoleText("      Nazwisko: " + surname), comp);
            comp.gridy = 3;
            centPanel.add(stworzPoleText("      PESEL: " + pesel), comp);
            comp.gridy = 4;
            centPanel.add(stworzPoleText("      Data urodzenia: " + data), comp);
            comp.gridy = 5;
            centPanel.add(stworzPoleText("      Numer telefonu: " + tel), comp);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Powrót");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            FramePacjenta pacFrame = new FramePacjenta(idPacjenta);
            pacFrame.setVisible(true);
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
