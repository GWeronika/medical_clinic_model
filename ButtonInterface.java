import javax.swing.*;
import java.awt.*;

public interface ButtonInterface {
    JButton stworzPrzycisk(String textPrzycisku);
    GridBagConstraints ustawPrzycisk(Insets insets, int x, int y);
}
