package domain;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class BrainStein extends Zombie {
    private int cerebrosGenerados;
    private Timer timer;

    public BrainStein(ImageIcon imageZombie, int[] posicion, boolean activo){
        super("brainStein",posicion);
        this.imagenZombie = imageZombie;
        this.activo = activo;
        this.cerCost = 50;
        this.vida = 300;
        this.cerebrosGenerados = 0;
        this.damage = 0;
    }

    private void startGeneratingCerebros() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cerebrosGenerados += 25;
                System.out.println("Cerebros generados: " + cerebrosGenerados);
            }
        }, 0, 20000);

    }

    public int getTotalCerebros() {
        return cerebrosGenerados;
    }

    public void resetCerebrosGenerados() {
        cerebrosGenerados = 0;
    }

    public void stopGeneratingCerebros() {
        if (timer != null) {
            timer.cancel();
            System.out.println("Generación de Cerebros detenida.");
        }
    }
}

