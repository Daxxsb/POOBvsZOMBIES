package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PVZGUIWindow2 extends JFrame {

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = (int) screenSize.getWidth() / 2;
    private int screenHeight = (int) screenSize.getHeight() / 2;
    private JLabel background, backgroundMenuLabel;
    private JLayeredPane layeredPane;
    private JPanel centralPanel, topRowPanel, bottomRowPanel;
    private JButton modalidadButton, modoButton, dificultadButton, okButton, noOkButton;
    private String modalidadSeleccionada = "";
    private String modoSeleccionado = "";
    private String dificultadSeleccionada = "";
    private List<String> plantasSeleccionadas = new ArrayList<String>();
    List<String> plantasConNombresModificados = new ArrayList<>();

    public PVZGUIWindow2() {
        super("POOBvsZombies");
        prepareElements();
        prepareActions();
        setVisible(true);
    }

    private void prepareElements() {
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        prepareElementsBackground2Window();
    }

    private void prepareElementsBackground2Window() {
        background = new JLabel(new ImageIcon(scaleImage("img/Fondos/2.png", screenWidth, screenHeight)));
        background.setBounds(0, 0, screenWidth, screenHeight);
        setContentPane(background);
        prepareElementsOptionsMenuTable();
    }

    private void prepareElementsOptionsMenuTable() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(screenWidth / 4, screenHeight / 4, screenWidth / 2, screenHeight / 2);

        ImageIcon backgroundIcon = new ImageIcon(scaleImage("img/Fondos/Tablero.png", screenWidth / 2, screenHeight / 2));
        backgroundMenuLabel = new JLabel(backgroundIcon);
        backgroundMenuLabel.setBounds(0, 0, screenWidth / 2, screenHeight / 2);
        layeredPane.add(backgroundMenuLabel, Integer.valueOf(0));

        centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout());
        centralPanel.setBounds(0, 50, screenWidth / 2, screenHeight / 2);
        centralPanel.setOpaque(false);

        topRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topRowPanel.setOpaque(false);

        bottomRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomRowPanel.setOpaque(false);

        ImageIcon modalidadIcon = new ImageIcon(scaleImage("img/Botones/Modalidad.png", 60, 60));
        modalidadButton = new JButton(modalidadIcon);
        modalidadButton.setBorderPainted(false);
        modalidadButton.setContentAreaFilled(false);

        ImageIcon modoIcon = new ImageIcon(scaleImage("img/Botones/Modo.png", 60, 60));
        modoButton = new JButton(modoIcon);
        modoButton.setBorderPainted(false);
        modoButton.setContentAreaFilled(false);

        ImageIcon dificultadIcon = new ImageIcon(scaleImage("img/Botones/Dificultad.png", 60, 60));
        dificultadButton = new JButton(dificultadIcon);
        dificultadButton.setBorderPainted(false);
        dificultadButton.setContentAreaFilled(false);

        ImageIcon okIcon = new ImageIcon(scaleImage("img/Botones/Ok.png", 30, 30));
        okButton = new JButton(okIcon);
        okButton.setBorderPainted(false);
        okButton.setContentAreaFilled(false);

        ImageIcon noOkIcon = new ImageIcon(scaleImage("img/Botones/NoOK.png", 30, 30));
        noOkButton = new JButton(noOkIcon);
        noOkButton.setBorderPainted(false);
        noOkButton.setContentAreaFilled(false);

        topRowPanel.add(modalidadButton);
        topRowPanel.add(modoButton);
        topRowPanel.add(dificultadButton);

        bottomRowPanel.add(okButton);
        bottomRowPanel.add(noOkButton);

        centralPanel.add(topRowPanel, BorderLayout.NORTH);
        centralPanel.add(bottomRowPanel, BorderLayout.CENTER);

        layeredPane.add(centralPanel, Integer.valueOf(1));

        background.add(layeredPane);
    }

    private void prepareActions() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prepareActionsButtons();
    }

    private void prepareActionsButtons() {
        modalidadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] opcionesModalidad = {"PvsP", "PvsM", "MvsM"};
                String seleccion = mostrarDialogoConOpciones("Seleccione la modalidad", opcionesModalidad);
                if (seleccion != null) {
                    modalidadSeleccionada = seleccion;
                    System.out.println("Modalidad seleccionada: " + seleccion);
                    if ("PvsP".equals(seleccion) || "MvsM".equals(seleccion)) {
                        dificultadButton.setEnabled(false);
                        dificultadSeleccionada = "";
                        plantasSeleccionadas.clear();
                    } else {
                        dificultadButton.setEnabled(true);
                    }
                }
            }
        });

        modoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] opcionesModo = {"Día"};
                String seleccion = mostrarDialogoConOpciones("Seleccione el modo", opcionesModo);
                if (seleccion != null) {
                    modoSeleccionado = seleccion;
                    System.out.println("Modo seleccionado: " + seleccion);
                }
            }
        });

        dificultadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dificultadButton.isEnabled()) {
                    String[] opcionesDificultad = {"Fácil", "Medio", "Difícil"};
                    String seleccion = mostrarDialogoConOpciones("Seleccione la dificultad", opcionesDificultad);
                    if (seleccion != null) {
                        dificultadSeleccionada = seleccion;
                        System.out.println("Dificultad seleccionada: " + seleccion);
                    }
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConfigurationValid()) {
                    if ("PvsP".equals(modalidadSeleccionada) || "MvsM".equals(modalidadSeleccionada)) {
                        mostrarConfiguracionSeleccionada();
                    } else {
                        abrirSeleccionPlantas();
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            PVZGUIWindow2.this,
                            "Por favor, complete toda la configuración antes de continuar.",
                            "Configuración incompleta",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        noOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        PVZGUIWindow2.this,
                        "¿Estás seguro de que quieres salir del juego?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private String mostrarDialogoConOpciones(String mensaje, String[] opciones) {
        JComboBox<String> comboBox = new JComboBox<>(opciones);
        int resultado = JOptionPane.showConfirmDialog(
                this,
                comboBox,
                mensaje,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            return (String) comboBox.getSelectedItem();
        }
        return null;
    }

    private Image scaleImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        return icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private boolean isConfigurationValid() {
        if (modalidadSeleccionada.isEmpty()) {
            return false;
        }
        if (modoSeleccionado.isEmpty()) {
            return false;
        }
        if (dificultadButton.isEnabled() && dificultadSeleccionada.isEmpty()) {
            return false;
        }
        return true;
    }

    private void abrirSeleccionPlantas() {
        JButton planta1Button = crearBotonPlanta("img/Plantas/Planta 1.jpg", "Planta 1");
        JButton planta2Button = crearBotonPlanta("img/Plantas/Planta 2.jpg", "Planta 2");
        JButton planta3Button = crearBotonPlanta("img/Plantas/Planta 3.jpg", "Planta 3");

        JPanel plantasPanel = new JPanel(new FlowLayout());
        plantasPanel.setOpaque(false);

        plantasPanel.add(planta1Button);
        plantasPanel.add(planta2Button);
        plantasPanel.add(planta3Button);

        int option = JOptionPane.showConfirmDialog(
                PVZGUIWindow2.this,
                plantasPanel,
                "Seleccione una o más plantas para jugar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION && !plantasSeleccionadas.isEmpty()) {
            mostrarConfiguracionSeleccionada();
        }
    }

    private JButton crearBotonPlanta(String rutaImagen, String nombrePlanta) {
        ImageIcon plantaIcon = new ImageIcon(scaleImage(rutaImagen, 60, 60));
        JButton plantaButton = new JButton(plantaIcon);
        plantaButton.setBorderPainted(true);
        plantaButton.setContentAreaFilled(false);
        plantaButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        plantaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (plantasSeleccionadas.contains(nombrePlanta)) {
                    plantasSeleccionadas.remove(nombrePlanta);
                    plantaButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                } else {
                    plantasSeleccionadas.add(nombrePlanta);
                    plantaButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                }
            }
        });

        return plantaButton;
    }

    private void mostrarConfiguracionSeleccionada() {

        String mensaje = "Configuración completa:\n" +
                "Modalidad: " + modalidadSeleccionada + "\n" +
                "Modo: " + modoSeleccionado;

        if ("PvsM".equals(modalidadSeleccionada))  {
            mensaje += "\nDificultad: " + dificultadSeleccionada;

            for (String planta : plantasSeleccionadas) {
                switch (planta) {
                    case "Planta 1":
                        plantasConNombresModificados.add("Sunflower");
                        break;
                    case "Planta 2":
                        plantasConNombresModificados.add("Peashooter");
                        break;
                    case "Planta 3":
                        plantasConNombresModificados.add("Wall-nut");
                        break;
                    default:
                        plantasConNombresModificados.add(planta);
                        break;
                }
            }

            String plantasSeleccionadasStr = String.join(", ", plantasConNombresModificados);
            mensaje += "\nPlantas seleccionadas: " + plantasSeleccionadasStr;
        }

        JOptionPane.showMessageDialog(
                PVZGUIWindow2.this,
                mensaje,
                "Configuración final",
                JOptionPane.INFORMATION_MESSAGE
        );

        abrirTablero();
    }

    private void abrirTablero() {
        PVZGUIWindow3 tableroWindow = new PVZGUIWindow3(
                modalidadSeleccionada,
                modoSeleccionado,
                dificultadSeleccionada,
                plantasConNombresModificados
        );

        tableroWindow.setVisible(true);

        dispose();
    }
}
