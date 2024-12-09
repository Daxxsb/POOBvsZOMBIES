package domain;

public class PeasShooter extends Plant{

    public PeasShooter(int vida, String tipo, int[] xy){
        super(vida, tipo, xy);
        sunCost = 50;
        plantado = true;
    }
}
