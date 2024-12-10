package presentation;
import domain.PVZGame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PVZGUIWindow3 extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = (int) screenSize.getWidth() / 2;
    private int screenHeight = (int) screenSize.getHeight() / 2;
    private JLabel background;
    private String modalidad;
    private String modo;
    private String dificultad;
    private List<String> plantasSeleccionadas;
    private String plantaActualSeleccionada;
    private int[] posicionBotonSeleccionado = new int[2];
    private JButton[][] buttonMatrix;
    private Random random = new Random();
    private PVZGame game;
    private Timer zombieMoverTimer;

    public PVZGUIWindow3(String modalidad, String modo, String dificultad, List<String> plantasSeleccionadas) {
        super("POOBvsZombies");
        this.modalidad = modalidad;
        this.modo = modo;
        this.dificultad = dificultad;
        this.plantasSeleccionadas = plantasSeleccionadas;

        prepareElements();
        prepareActions();
        setVisible(true);

        game = new PVZGame();
    }

    private void prepareElements() {
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        prepareElementsBackground3Window();
        preparesElementsBoard();
        addSunBar();
    }

    private void prepareElementsBackground3Window() {
        background = new JLabel(new ImageIcon(scaleImage("img/Fondos/3.png", screenWidth, screenHeight)));
        background.setBounds(0, 0, screenWidth, screenHeight);
        setContentPane(background);
    }

    private void preparesElementsBoard() {
        addButtonsToMatrix();
    }

    private void addButtonsToMatrix() {
        int rows = 5;
        int cols = 8;
        int casaWidth = screenWidth / 6;
        int cespedWidth = 4 * casaWidth;
        int startY = 50;

        int cellWidth = (cespedWidth - casaWidth) / cols;
        int cellHeight = (screenHeight - startY) / rows - 10;

        buttonMatrix = new JButton[rows][cols];

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = casaWidth + col * cellWidth;
                int y = startY + row * cellHeight;

                JButton button = new JButton();
                button.setBounds(x, y, cellWidth, cellHeight);

                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setFocusPainted(false);

                final int currentRow = row;
                final int currentCol = col;
                button.addActionListener(e -> {
                    posicionBotonSeleccionado[0] = currentRow;
                    posicionBotonSeleccionado[1] = currentCol;
                    System.out.println("Botón seleccionado en: [" + currentRow + ", " + currentCol + "]");
                    if (plantaActualSeleccionada != null) {
                        System.out.println("Planta seleccionada: " + plantaActualSeleccionada);
                        if (game.gastoSoles(plantaActualSeleccionada)) {
                            addPlantstoMatrix(plantaActualSeleccionada, posicionBotonSeleccionado);
                            plantaActualSeleccionada = null;
                        } else {
                            JOptionPane.showMessageDialog(null, "Cantidad de soles insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                buttonPanel.add(button);
                buttonMatrix[row][col] = button;
            }
        }
        buttonPanel.setBounds(0, 0, screenWidth, screenHeight);
        background.add(buttonPanel);
        background.revalidate();

        SwingUtilities.invokeLater(this::addPodadorasToMatrix);
        SwingUtilities.invokeLater(this::addZombiesToMatrix);
    }

    private void addPlantstoMatrix(String plantaActualSeleccionada, int[] posicionBotonSeleccionado) {
        if (plantaActualSeleccionada == null || posicionBotonSeleccionado == null) {
            plantaActualSeleccionada = null;
            posicionBotonSeleccionado = null;
            JOptionPane.showMessageDialog(null, "No se ha seleccionado una planta o posición.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int row = posicionBotonSeleccionado[0];
        int col = posicionBotonSeleccionado[1];

        String[] availablePositions = game.getAvailablePositions(false);

        String selectedPosition = "Fila " + (row + 1) + ", Columna " + (col + 1);

        boolean isPositionAvailable = Arrays.asList(availablePositions).contains(selectedPosition);

        if (!isPositionAvailable) {
            plantaActualSeleccionada = null;
            posicionBotonSeleccionado = null;
            JOptionPane.showMessageDialog(null, "La posición seleccionada no está disponible para colocar una planta.", "Error", JOptionPane.ERROR_MESSAGE);
            game.agregarSoles(50);
            return;
        }

        ImageIcon imagen = game.getImagenPlanta(game.placePlant(plantaActualSeleccionada, row, col));
        JButton button = buttonMatrix[row][col];
        button.setIcon(imagen);

        System.out.println("Planta " + plantaActualSeleccionada + " colocada en " + selectedPosition);

    }

    private void addPodadorasToMatrix() {
        int rows = 5;
        for (int row = 0; row < rows; row++) {
            JButton button = buttonMatrix[row][0];
            int[] posicion = {row, 0};
            ImageIcon imagen = game.getImagenPlanta(game.placePodadora(posicion));
            button.setIcon(imagen);
        }
    }

    private void addZombiesToMatrix() {
        moveZombies();
        if ("Fácil".equals(dificultad)) {
            Timer zombieSpawnerTimer = new Timer(25000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = random.nextInt(5);
                    int col = 7;

                    JButton button = buttonMatrix[row][col];
                    ImageIcon imagen = game.getImagenZombie(game.placeZombie("NormalZombie", row, col));
                    button.setIcon(imagen);
                }
            });

            zombieSpawnerTimer.setDelay(25000);
            zombieSpawnerTimer.setRepeats(true);
            zombieSpawnerTimer.start();
        }
    }

    private void moveZombies() {
        if (zombieMoverTimer == null) {
            zombieMoverTimer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int row = 0; row < buttonMatrix.length; row++) {
                        for (int col = buttonMatrix[row].length - 1; col > 0; col--) {
                            if (game.getZombiesActivates(row, col)) {
                                game.moveZombies(row, col);

                                JButton currentButton = buttonMatrix[row][col];
                                JButton nextButton = buttonMatrix[row][col - 1];

                                // ImageIcon imagen = game.getImagenZombie(game.placeZombie("NormalZombie", row, col));
                                // nextButton.setIcon(imagen);
                                ImageIcon zombieIcon = (ImageIcon) currentButton.getIcon();
                                nextButton.setIcon(zombieIcon);
                                currentButton.setIcon(null);
                                game.imprimirTableros();
                                break;
                            }
                        }
                    }
                }
            });

            zombieMoverTimer.setRepeats(true);
            zombieMoverTimer.start();
        }
    }


    private void addSunBar() {
        JPanel sunBarPanel = new JPanel();
        sunBarPanel.setLayout(null);
        sunBarPanel.setBounds(0, 0, screenWidth / 3, screenHeight / 8);
        sunBarPanel.setOpaque(false);

        ImageIcon barIcon = new ImageIcon("img/Fondos/bar.jpeg");
        JLabel barLabel = new JLabel();
        barLabel.setIcon(new ImageIcon(barIcon.getImage().getScaledInstance(sunBarPanel.getWidth(), sunBarPanel.getHeight(), Image.SCALE_SMOOTH)));
        barLabel.setBounds(0, 0, sunBarPanel.getWidth(), sunBarPanel.getHeight());
        sunBarPanel.add(barLabel);

        JButton sunButton = new JButton("0");
        sunButton.setBounds(0, 20, 50, 50);
        sunButton.setContentAreaFilled(false);
        sunButton.setFocusPainted(false);
        sunButton.setBorderPainted(false);
        sunButton.setOpaque(false);

        sunButton.setHorizontalAlignment(SwingConstants.CENTER);
        sunButton.setVerticalAlignment(SwingConstants.BOTTOM);

        sunButton.setMargin(new Insets(7, 5, 0, 5));

        Timer timer = new Timer(100, e -> {
            sunButton.setText(String.valueOf(game.obtenerSoles()));
        });
        timer.start();

        sunButton.addActionListener(e -> {
            int solesGenerados = game.calcularSumarSoles();
            game.agregarSoles(solesGenerados);
        });

        JButton sunflowerButton = createPlantButton("img/Plantas/sunflowerCard.png", 60, 10, "Sunflower");
        sunflowerButton.addActionListener(e -> plantaActualSeleccionada = "Sunflower");

        JButton peashooterButton = createPlantButton("img/Plantas/peaShooterCard.png", 130, 10, "Peashooter");
        peashooterButton.addActionListener(e -> plantaActualSeleccionada = "Peashooter");

        JButton wallnutButton = createPlantButton("img/Plantas/wallnutCard.png", 200, 10, "Wall-nut");
        wallnutButton.addActionListener(e -> plantaActualSeleccionada = "Wall-nut");

        if ("PvsP".equals(modalidad)) {
            sunBarPanel.add(sunButton);
            sunBarPanel.add(sunflowerButton);
            sunBarPanel.add(peashooterButton);
            sunBarPanel.add(wallnutButton);
        } else if ("PvsM".equals(modalidad)) {
            sunBarPanel.add(sunButton);
            if (!plantasSeleccionadas.contains("Sunflower")) {
                sunflowerButton.setEnabled(false);
            } else {
                sunBarPanel.add(sunflowerButton);
            }
            if (!plantasSeleccionadas.contains("Peashooter")) {
                peashooterButton.setEnabled(false);
            } else {
                sunBarPanel.add(peashooterButton);
            }
            if (!plantasSeleccionadas.contains("Wall-nut")) {
                wallnutButton.setEnabled(false);
            } else {
                sunBarPanel.add(wallnutButton);
            }

        } else if ("MvsM".equals(modalidad)) {
            sunButton.setEnabled(false);
            sunflowerButton.setEnabled(false);
            peashooterButton.setEnabled(false);
            wallnutButton.setEnabled(false);
            sunBarPanel.add(sunButton);
            sunBarPanel.add(sunflowerButton);
            sunBarPanel.add(peashooterButton);
            sunBarPanel.add(wallnutButton);
        }

        sunBarPanel.setComponentZOrder(barLabel, 1);
        sunBarPanel.setComponentZOrder(sunButton, 0);
        sunBarPanel.setComponentZOrder(sunflowerButton, 0);
        sunBarPanel.setComponentZOrder(peashooterButton, 0);
        sunBarPanel.setComponentZOrder(wallnutButton, 0);

        background.add(sunBarPanel, Integer.valueOf(2));
        background.revalidate();
        background.repaint();
    }

    private JButton createPlantButton(String imagePath, int x, int y, String toolTip) {
        JButton button = new JButton();
        button.setBounds(x, y, 50, 50);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        ImageIcon plantIcon = new ImageIcon(imagePath);
        button.setIcon(new ImageIcon(plantIcon.getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH)));

        button.setToolTipText(toolTip);
        return button;
    }

    private void prepareActions() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Image scaleImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        return icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
