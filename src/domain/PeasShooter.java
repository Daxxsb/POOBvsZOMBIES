package domain;

import javax.swing.*;

public class PeasShooter extends Plant{

    public PeasShooter(String tipo, int[] xy){
        super(tipo, xy);
        vida = 300;
        sunCost = 50;
        plantado = true;
        direccionImagenPlanta = "img/Plantas/peashooter.gif";
        imagenPlanta = new ImageIcon(direccionImagenPlanta);
    }
}
