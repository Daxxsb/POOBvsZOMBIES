package domain;

import javax.swing.*;

public class WallNut extends Plant{

    public WallNut(String tipo, int[] xy){
        super(tipo, xy);
        vida = 40000;
        sunCost = 50;
        plantado = true;
        direccionImagenPlanta = "img/Plantas/wall-nut.gif";
        imagenPlanta = new ImageIcon(direccionImagenPlanta);
    }
}
