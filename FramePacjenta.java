import javax.swing.*;
import java.awt.*;

public class FramePacjenta extends JFrame implements ButtonInterface, PanelInterface {
    public FramePacjenta(int idPacjenta) {
        super("Panel pacjenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        //tworzenie głównego kontenera JPanel z przyciskami
        JPanel mainPanel = new JPanel(new BorderLayout());

        //tworzenie panelu centralnego, w którym będą wszystkie przyciski i nagłówek
        JPanel centPanel = new JPanel(new GridBagLayout());

        //tworzenie panelu na przycisk powrotu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //tworzenie napisu JLabel
        JLabel welcomeLabel = new JLabel("Panel pacjenta");
        welcomeLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));          //ustawienie rozmiaru napisu na 60 pikseli


        //tworzenie przycisków funkcjonalnościowych dla pacjenta
        JButton data = stworzPrzycisk("Dane osobowe");
        data.setFocusPainted(false);
        dodajDoPanelu(mainPanel, data);
        data.addActionListener(e -> {
            DanePacjenta daneP = new DanePacjenta(idPacjenta);
            daneP.setVisible(true);
            setVisible(false);
        });

        JButton visit = stworzPrzycisk("Wizyty");
        dodajDoPanelu(mainPanel, visit);
        visit.addActionListener(e -> {
            WizytyPacjenta wizyta = new WizytyPacjenta(idPacjenta);
            wizyta.setVisible(true);
            setVisible(false);
        });

        JButton exam = stworzPrzycisk("Badania");
        dodajDoPanelu(mainPanel, exam);
        exam.addActionListener(e -> {
            BadaniaPacjenta badPac = new BadaniaPacjenta(idPacjenta);
            badPac.setVisible(true);
            setVisible(false);
        });

        JButton referral = stworzPrzycisk("Skierowania");
        dodajDoPanelu(mainPanel, referral);
        dodajDoPanelu(mainPanel, referral);
        referral.addActionListener(e -> {
            SkierowaniaPacjenta skierowanieP = new SkierowaniaPacjenta(idPacjenta);
            skierowanieP.setVisible(true);
            setVisible(false);
        });

        JButton presc = stworzPrzycisk("Recepty");
        dodajDoPanelu(mainPanel, presc);
        presc.addActionListener(e -> {
            ReceptyPacjenta receptaP = new ReceptyPacjenta(idPacjenta);
            receptaP.setVisible(true);
            setVisible(false);
        });

        JButton hist = stworzPrzycisk("Historia leczenia");
        dodajDoPanelu(mainPanel, hist);
        hist.addActionListener(e -> {
            HistoriaLeczenia histL = new HistoriaLeczenia(idPacjenta);
            histL.setVisible(true);
            setVisible(false);
        });

        //ustawianie wyśrodkowania napisu i przycisków
        Insets insets = new Insets(0, 0, 30, 0);        //margines dolny 30 pikseli
        GridBagConstraints constraints = ustawPrzycisk(insets, 0, 0);
        centPanel.add(welcomeLabel, constraints);

        insets = new Insets(10, 0, 0, 0);               //margines górny 10 pikseli
        constraints = ustawPrzycisk(insets, 0, 1);
        centPanel.add(data, constraints);
        constraints = ustawPrzycisk(insets, 0, 2);
        centPanel.add(visit, constraints);
        constraints = ustawPrzycisk(insets, 0, 3);
        centPanel.add(exam, constraints);
        constraints = ustawPrzycisk(insets, 0, 4);
        centPanel.add(referral, constraints);
        constraints = ustawPrzycisk(insets, 0, 5);
        centPanel.add(presc, constraints);
        constraints = ustawPrzycisk(insets, 0, 6);
        centPanel.add(hist, constraints);

        //dodawanie przycisku Powrót do panelu na dole
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
