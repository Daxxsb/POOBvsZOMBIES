package domain;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Zombie extends Thread implements Serializable {
    protected int vida;
    protected String tipo;
    protected String direccionImagenZombie;
    protected ImageIcon imagenZombie;
    protected int[] posicion;
    protected boolean activo = false;
    protected int damage;
    protected float rapidezAtaque;
    protected int cerCost;

    public Zombie(String tipo, int[] posicion){
        this.tipo = tipo;
        this.posicion = posicion;
    }
    public ImageIcon getImagenZombie() {
        return imagenZombie;
    }

    public int moveZombie() {
        if (posicion[1] >= 0) {
            posicion[1] -= 1;
            activo = true;
        }
        return  posicion[1];
    }
}
