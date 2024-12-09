package domain;

public class WallNut extends Plant{

    public WallNut(int vida, String tipo, int[] xy){
        super(vida, tipo, xy);
        sunCost = 50;
        plantado = true;
    }
}
