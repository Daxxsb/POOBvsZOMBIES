package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PVZGUIWindow1 extends JFrame {

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = (int) screenSize.getWidth() / 2;
    private int screenHeight = (int) screenSize.getHeight() / 2;
    private JLabel background;
    private JLabel headerLabel;
    private JButton startButton;

    public PVZGUIWindow1() {
        super("POOBvsZombies");
        prepareElements();
        prepareActions();
        setVisible(true);
    }

    private void prepareElements() {
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        prepareElementsBackground1Window();
    }

    private void prepareElementsBackground1Window() {
        background = new JLabel(new ImageIcon(scaleImage("img/Fondos/1.png", screenWidth, screenHeight)));
        background.setBounds(0, 0, screenWidth, screenHeight);
        setContentPane(background);

        headerLabel = new JLabel(new ImageIcon(scaleImage("img/Fondos/HeaderPOOBvsZOMBIES2.png", 400, 400)));
        headerLabel.setBounds(screenWidth / 2 - 200, 5, 400, 400); // Centrado
        add(headerLabel);

        ImageIcon startIcon = new ImageIcon(scaleImage("img/Botones/Inicio.png", 60, 60));
        startButton = new JButton(startIcon);
        startButton.setBounds(screenWidth / 2 - 30, screenHeight - 125, 60, 60);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        add(startButton);
    }

    private void prepareActions() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prepareActionsButtons();
    }

    private void prepareActionsButtons() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PVZGUIWindow2();
            }
        });
    }

    private Image scaleImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        return icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static void main(String[] args) {
        new PVZGUIWindow1();
    }
}
