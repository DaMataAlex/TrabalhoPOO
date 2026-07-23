import gui.FazendaGUI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FazendaGUI gui = new FazendaGUI();
            gui.setVisible(true);
        });
    }
}
