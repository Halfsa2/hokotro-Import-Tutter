package megjelenites;
import java.awt.*;
import javax.swing.*;

public class StartFrame extends JFrame {
    public StartFrame() {
        setTitle("Zúzmaraváros - Menü");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Középre igazítja az ablakot
        setLayout(new GridLayout(3, 1, 10, 10)); // 3 soros elrendezés

        JLabel titleLabel = new JLabel("Hókotró Szimulátor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);

        JButton newGameButton = new JButton("Új játék");
        newGameButton.addActionListener(e -> startNewGame());
        add(newGameButton);

        JButton loadGameButton = new JButton("Játék folytatása");
        loadGameButton.addActionListener(e -> loadGame());
        add(loadGameButton);
    }

    private void startNewGame() {
        // TODO: VarosModell és JatekVezerlo inicializálása
        // IJatekVezerlo vezerlo = new JatekVezerlo(...);
        // vezerlo.initJatek(); 
        
        // GameWindow megnyitása és a StartFrame bezárása
        // GameWindow gw = new GameWindow(vezerlo);
        // gw.setVisible(true);
        // this.dispose();
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            // TODO: A CommandInterpreter 'load' logikájának meghívása a fájlra
            // Majd GameWindow megnyitása
        }
    }
}