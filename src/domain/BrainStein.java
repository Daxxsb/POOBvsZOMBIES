package domain;

import java.util.Timer;
import java.util.TimerTask;

public class BrainStein extends Zombie {
    private int cerebrosGenerados;
    private Timer timer;

    public BrainStein(int vida, String tipo, int[] xy) {
        vida = 0;
        tipo = "si";
        cerCost = 50;
        cerebrosGenerados = 0;
        startGeneratingCerebros();
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

