package domain;

import java.util.Timer;
import java.util.TimerTask;

public class Sunflower extends Plant {
    private int solesGenerados;
    private Timer timer;

    public Sunflower(int vida, String tipo, int[] xy) {
        super(vida, tipo, xy);
        sunCost = 50;
        plantado = true;
        solesGenerados = 0;
        startGeneratingSoles();
    }

    private void startGeneratingSoles() {
        if (plantado) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    solesGenerados += 25;
                    System.out.println("Soles generados: " + solesGenerados);
                }
            }, 0, 20000);
        }
    }

    public int getTotalSoles() {
        return solesGenerados;
    }

    public void resetSolesGenerados() {
        solesGenerados = 0;
    }

    public void stopGeneratingSoles() {
        if (timer != null) {
            timer.cancel();
            System.out.println("Generación de soles detenida.");
        }
    }
}