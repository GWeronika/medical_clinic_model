import javax.swing.*;
import java.awt.*;

public class FrameLekarza extends JFrame implements ButtonInterface, PanelInterface {
    public FrameLekarza(int idLekarza) {
        super("Panel lekarza");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel
        JPanel mainPanel = new JPanel(new BorderLayout());

        //tworzenie panelu centralnego, w którym będą wszystkie przyciski i nagłówek
        JPanel centPanel = new JPanel(new GridBagLayout());

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie napisu JLabel
        JLabel welcomeLabel = new JLabel("Panel lekarza " + idLekarza);
        welcomeLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));           //ustawienie rozmiaru napisu na 60 pikseli


        //tworzenie przycisków funkcjonalnościowych dla lekarza
        JButton data = stworzPrzycisk("Dane osobowe");
        data.setFocusPainted(false);
        dodajDoPanelu(mainPanel, data);
        data.addActionListener(e -> {
            DaneLekarza daneL = new DaneLekarza(idLekarza);
            daneL.setVisible(true);
            setVisible(false);
        });

        JButton visit = stworzPrzycisk("Wizyty");
        dodajDoPanelu(mainPanel, visit);
        visit.addActionListener(e -> {
            WizytyLekarza wizyta = new WizytyLekarza(idLekarza);
            wizyta.setVisible(true);
            setVisible(false);
        });

        JButton refb = stworzPrzycisk("Skierowania na badania");
        dodajDoPanelu(mainPanel, refb);
        refb.addActionListener(e -> {
            SkierowanieLekarzaBadania badanie = new SkierowanieLekarzaBadania(idLekarza);
            badanie.setVisible(true);
            setVisible(false);
        });

        JButton referral = stworzPrzycisk("Skierowania do specjalisty");      //tu będą dodawane kolejne skierowania
        dodajDoPanelu(mainPanel, referral);
        referral.addActionListener(e -> {
            SkierowaniaLekarza skierowanieL = new SkierowaniaLekarza(idLekarza);
            skierowanieL.setVisible(true);
            setVisible(false);
        });

        JButton presc = stworzPrzycisk("Recepty");         //tu będą dodawane kolejne recepty
        dodajDoPanelu(mainPanel, presc);
        presc.addActionListener(e -> {
            ReceptyLekarza receptaL = new ReceptyLekarza(idLekarza);
            receptaL.setVisible(true);
            setVisible(false);
        });

        //wyśrodkowania napisu i przycisków
        Insets insets = new Insets(0, 0, 30, 0);        //margines dolny 30 pikseli
        GridBagConstraints constraints = ustawPrzycisk(insets, 0, 0);
        centPanel.add(welcomeLabel, constraints);

        insets = new Insets(10, 0, 0, 0);               //margines górny 10 pikseli
        constraints = ustawPrzycisk(insets, 0, 1);
        centPanel.add(data, constraints);
        constraints = ustawPrzycisk(insets, 0, 2);
        centPanel.add(visit, constraints);
        constraints = ustawPrzycisk(insets, 0, 3);
        centPanel.add(refb, constraints);
        constraints = ustawPrzycisk(insets, 0, 4);
        centPanel.add(referral, constraints);
        constraints = ustawPrzycisk(insets, 0, 5);
        centPanel.add(presc, constraints);

        //dodanie przycisku Powrót do panelu na dole
        JButton goBackButton = new JButton("Wyloguj się");
        bottomPanel.add(goBackButton);
        goBackButton.addActionListener(e -> {
            PageStart startFrame = new PageStart();
            startFrame.setVisible(true);
            setVisible(false);
        });

        //dodanie panelu centralnego do głównego
        mainPanel.add(centPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        add(mainPanel);     //dodanie panelu głównego do okna

        setVisible(true);
    }
    public void dodajDoPanelu(JPanel panel, JButton button) {      //funkcja, która dodaje do każdego przycisku jego rozmiary, położenie i reagowanie na wciśnięcie
        panel.add(Box.createVerticalGlue());
        panel.add(button);
        panel.add(Box.createVerticalGlue());        //drugi raz żeby na dole też był odstęp
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
