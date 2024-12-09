package domain;

public class Pea {
    private int x, y;
    private boolean active;

    public Pea(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = true;
    }

    public void move() {
        if (active) {
            x++;
        }
    }

    public void checkCollision() {
    }

    public void setInactive() {
        active = false; // Desactiva el guisante si colisiona con un zombi
    }
}

