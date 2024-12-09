package domain;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Zombie extends Thread implements Serializable {
    protected int hp;
    protected String nombre;
    protected Rectangle hitBox;
    protected ImageIcon normal;
    protected int damage;
    protected int cerCost;
    protected transient ArrayList<Plant> plantas = new ArrayList();
    protected transient ArrayList<Zombie> zombies = new ArrayList();
    protected JLabel zombieLabel = new JLabel();
    protected transient JPanel gamePanel;

    private static final long SerialVersionUID = 777;
    public Zombie(){
    }

    public Zombie(String nombre, int hp, ImageIcon normal, Rectangle hitbox, JPanel gamePanel){
        this.nombre = nombre;
        this.hp = hp;
        this.normal = normal;


        this.hitBox = hitbox;
        zombieLabel.setBounds(hitbox);
        zombieLabel.setIcon(normal);
        this.gamePanel = gamePanel;

    }

    public void dmg(int damage){
        this.hp -= damage;
    }



}
