import javax.swing.*;
import java.awt.*;

public class FramePielegniarki extends JFrame implements ButtonInterface, PanelInterface {
    public FramePielegniarki(int idPielegniarki) {
        super("Panel pielegniarki");

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
        JLabel welcomeLabel = new JLabel("Panel pielęgniarki " + idPielegniarki);
        welcomeLabel.setFont(new Font("Montserrat", Font.PLAIN, 32));       //rozmiar napisu


        //tworzenie przycisków funkcjonalnościowych dla lekarza
        JButton data = stworzPrzycisk("Dane osobowe");
        dodajDoPanelu(mainPanel, data);
        data.addActionListener(e -> {
            DanePielegniarki daneP = new DanePielegniarki(idPielegniarki);
            daneP.setVisible(true);
            setVisible(false);
        });

        JButton exam = stworzPrzycisk("Badania planowe");     //tu będą dodawane kolejne wizyty za pacjenta
        dodajDoPanelu(mainPanel, exam);
        exam.addActionListener(e -> {
            BadaniePielegniarki badanieP = new BadaniePielegniarki(idPielegniarki);
            badanieP.setVisible(true);
            setVisible(false);
        });

        JButton visit = stworzPrzycisk("Wizyty");     //tu będą dodawane kolejne wizyty za pacjenta
        dodajDoPanelu(mainPanel, visit);
        visit.addActionListener(e -> {
            WizytyPielegniarki wizytaP = new WizytyPielegniarki(idPielegniarki);
            wizytaP.setVisible(true);
            setVisible(false);
        });


        // Ustawianie wyśrodkowania napisu i przycisków
        Insets insets = new Insets(0, 0, 30, 0);    //margines dolny 30 pikseli
        GridBagConstraints constraints = ustawPrzycisk(insets, 0, 0);
        centPanel.add(welcomeLabel, constraints);

        insets = new Insets(10, 0, 0, 0);           //margines górny 10 pikseli
        constraints = ustawPrzycisk(insets, 0, 1);
        centPanel.add(data, constraints);
        constraints = ustawPrzycisk(insets, 0, 2);
        centPanel.add(exam, constraints);
        constraints = ustawPrzycisk(insets, 0, 3);
        centPanel.add(visit, constraints);

        // Dodawanie przycisku Powrót do panelu na dole
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
    public JButton stworzPrzycisk(String textPrzycisku) {
        JButton button = new JButton(textPrzycisku);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));        //ustawienie rozmiarów przycisku
        return button;
    }
    public void dodajDoPanelu(JPanel panel, JButton button) {      //funkcja, która dodaje do każdego do panelu z odpowiednimi odstępami
        panel.add(Box.createVerticalGlue());
        panel.add(button);
        panel.add(Box.createVerticalGlue());        //drugi raz żeby na dole też był odstęp
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
