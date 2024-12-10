package domain;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Plant extends Thread implements Serializable {

    protected int vida;
    protected String tipo;
    protected String direccionImagenPlanta;
    protected ImageIcon imagenPlanta;
    protected int sunCost;
    protected int[] posicion;
    protected boolean plantado = false;

    public Plant(String name, int[] xy){
        this.tipo = name;
        this.posicion = xy;
    }

    public boolean getPlantado() {
        return plantado;
    }

    public ImageIcon getImagenPlanta() {
        return imagenPlanta;
    }

}
