package domain;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Plant extends Thread implements Serializable {

    protected int vida;
    protected String tipo;
    protected int sunCost;
    protected int[] posicion;
    protected boolean plantado = false;

    public Plant(int hp, String name, int[] xy){
        this.vida = hp;
        this.tipo = name;
        this.posicion = xy;
    }

    public boolean getPlantado() {
        return plantado;
    }

}
