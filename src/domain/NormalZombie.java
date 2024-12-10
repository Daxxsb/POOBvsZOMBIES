package domain;

import javax.swing.*;

public class NormalZombie extends Zombie{
    public NormalZombie(String tipo, int[] posicion) {
        super(tipo, posicion);
        vida = 100;
        direccionImagenZombie = "img/Zombies/normalzombiewalk.gif";
        imagenZombie = new ImageIcon(direccionImagenZombie);
        activo = true;
        damage = 100;
        rapidezAtaque = 0.5f;
        cerCost = 100;
    }
}
