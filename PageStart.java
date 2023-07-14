import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PageStart extends JFrame implements ButtonInterface {

    public PageStart(){
        super("Przychodnia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel z układem BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        //tworzenie panelu z napisem i układem GridBagLayout
        JPanel topPanel = new JPanel(new GridBagLayout());

        //tworzenie panelu na przycisk Administrator
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        //tworzenie napisu JLabel
        JLabel welcomeLabel = new JLabel("Witamy w przychodni internetowej!");
        welcomeLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));       //ustawienie rozmiaru napisu

        //tworzenie przycisków
        JButton buttPortalPacjenta = stworzPrzycisk("Portal pacjenta");
        buttPortalPacjenta.setFocusPainted(false);
        buttPortalPacjenta.addActionListener(e -> {
            LoginPacjent LoginPacjentFrame = new LoginPacjent();
            LoginPacjentFrame.setVisible(true);
            setVisible(false);
        });

        JButton butPortalPracownika = stworzPrzycisk("Portal pracownika");
        butPortalPracownika.addActionListener(e -> {
            LoginPracownik LoginPracownikFrame = new LoginPracownik();
            LoginPracownikFrame.setVisible(true);
            setVisible(false);
        });

        JButton adminButton = stworzPrzycisk("Administrator");
        adminButton.addActionListener(e -> {
            String name=JOptionPane.showInputDialog("Wprowadż kod autoryzacji:");           //logowanie do konta admina
            if(Objects.equals(name, "12345")){
                FrameAdmina frAdm = new FrameAdmina();
                frAdm.setVisible(true);
                setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(PageStart.this, "Niepoprawny kod autoryzacji!");          //wyświetlenie okienka informacyjnego
            }
        });

        //ustawienia dla napisu i przycisków
        Insets insets = new Insets(0, 0, 30, 0);       //margines dolny 30 pikseli
        GridBagConstraints constraints = ustawPrzycisk(insets, 0 ,0);
        topPanel.add(welcomeLabel, constraints);

        insets = new Insets(10, 0, 0, 0);       //margines górny 10 pikseli
        constraints = ustawPrzycisk(insets, 0 ,1);
        topPanel.add(buttPortalPacjenta, constraints);

        constraints = ustawPrzycisk(insets, 0 ,2);
        topPanel.add(butPortalPracownika, constraints);

        //dodanie przycisku Administrator do panelu na dole
        bottomPanel.add(adminButton);

        //dodanie paneli do głównego panelu
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        //mainPanel.setBackground(Color.LIGHT_GRAY);

        //dodawanie głównego panelu do okna
        add(mainPanel);
    }
    public JButton stworzPrzycisk(String textPrzycisku) {          //funkcja do tworzenia i ustawiania własności przycisku
        JButton button = new JButton(textPrzycisku);
        Dimension buttonSize = new Dimension(200, 40);
        button.setPreferredSize(buttonSize);
        return button;
    }
    public GridBagConstraints ustawPrzycisk(Insets insets, int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = insets;
        constraints.anchor = GridBagConstraints.CENTER;                         //wyśrodkowanie wzdłuż osi X
        return constraints;
    }
}
