package domain;

import javax.swing.*;

public class Podadora extends Plant{

    public Podadora(String name, int[] xy) {
        super(name, xy);
        vida = 1000;
        sunCost = 0;
        plantado = true;
        direccionImagenPlanta = "img/Plantas/lawnmower.gif";
        imagenPlanta = new ImageIcon(direccionImagenPlanta);
    }
}
