package domain;

import javax.swing.*;
import java.io.Serializable;

public class PVZGame implements Serializable {
    private String[][] board;
    private Plant[][] plantas;
    private Zombie[][] zombies;
    private int soles;
    private int puntaje;

    public PVZGame() {
        board = new String[5][8];
        plantas = new Plant[5][8];
        zombies = new Zombie[5][8];
        puntaje = 0;
        soles = 50;
    }

    public String[][] getBoard() {
        return board;
    }

    public boolean getZombiesActivates(int row, int col) {
        return zombies[row][col] != null && zombies[row][col].activo;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public ImageIcon getImagenPlanta(Plant planta) {
        return planta.getImagenPlanta();
    }

    public ImageIcon getImagenZombie(Zombie zombie) {
        return zombie.getImagenZombie();
    }

    public Plant placePlant(String plant, int row, int col) {
        if (board[row][col] == null && col != 0 && col != 7) {
            board[row][col] = plant;

            Plant asignacionNuevaPlanta;
            int[] posicionesArreglo = {row, col};
            switch (plant) {
                case "Sunflower":
                    asignacionNuevaPlanta = new Sunflower("Sunflower", posicionesArreglo);
                    break;
                case "Peashooter":
                    asignacionNuevaPlanta = new PeasShooter("Peashooter", posicionesArreglo);
                    break;
                case "Wall-nut":
                    asignacionNuevaPlanta = new WallNut("Wall-nut", posicionesArreglo);
                   break;
                default:
                    return null;
            }

            plantas[row][col] = asignacionNuevaPlanta;
            puntaje += 10;
            return asignacionNuevaPlanta;
        }
        return null;
    }

    public Zombie placeZombie(String zombie, int row, int col) {
        board[row][col] = zombie;

        Zombie asignacionNuevoZombie;
        int[] posicionesArreglo = {row, col};
        switch (zombie) {
            case "NormalZombie":
                asignacionNuevoZombie = new NormalZombie("NormalZombie", posicionesArreglo);
                break;
            case "ConeHead":
                asignacionNuevoZombie = new ConeHead("ConeHead", posicionesArreglo);
                break;
            case "BucketHead":
                asignacionNuevoZombie = new BucketHead("BucketHead", posicionesArreglo);
                break;
            default:
                return null;
            }

            zombies[row][col] = asignacionNuevoZombie;
            return asignacionNuevoZombie;
    }

    public void moveZombies(int row, int col) {
        // Recupera el zombie que se moverá
        Zombie zombieAMover = zombies[row][col];

        if (zombieAMover != null) { // Asegúrate de que no sea null
            int nuevaCol = zombieAMover.moveZombie();
            zombies[row][nuevaCol] = zombieAMover;
            board[row][nuevaCol] = zombieAMover.tipo;
            zombies[row][col] = null;
            board[row][col] = "Empty";
        }
    }

    public Plant placePodadora(int[] posicion) {
        return new Podadora("podadora", posicion);
    }

    public boolean gastoSoles(String plant) {
        switch (plant) {
            case "Sunflower":
                if (soles >= 50) {
                    soles -= 50;
                    return true;
                }
                break;
            case "Peashooter":
                if (soles >= 100) {
                    soles -= 100;
                    return true;
                }
                break;
            case "Wall-nut":
                if (soles >= 50) {
                    soles -= 50;
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    public void agregarSoles(int cantidad) {
        soles += cantidad;
        System.out.println("Soles totales: " + soles);
    }

    public int obtenerSoles() {
        return soles;
    }

    public int calcularSumarSoles() {
        int solesSumados = 0;
        for (int i = 0; i < plantas.length; i++) {
            for (int j = 0; j < plantas[i].length; j++) {
                Plant planta = plantas[i][j];
                if (planta instanceof Sunflower) {
                    Sunflower sunflower = (Sunflower) planta;
                    solesSumados += sunflower.getTotalSoles(); // Acumula soles generados
                    sunflower.resetSolesGenerados(); // Reinicia los soles generados en la planta
                }
            }
        }
        return solesSumados;
    }

    public void imprimirTableros() {
        System.out.println("Estado de Board:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print((board[i][j] == null ? "Empty" : board[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    public boolean removePlant(int row, int col) {
        if (board[row][col] != null && col != 0 && col != 7) {
            board[row][col] = null;
            puntaje -= 5;
            return true;
        }
        return false;
    }

    public String[] getAvailablePositions(boolean isRemoval) {
        StringBuilder positions = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isRemoval && board[i][j] != null) {
                    positions.append("Fila ").append(i + 1).append(", Columna ").append(j + 1).append(";");
                }
                else if (!isRemoval && board[i][j] == null && j != 0 && j != 7) {
                    positions.append("Fila ").append(i + 1).append(", Columna ").append(j + 1).append(";");
                }
            }
        }
        return positions.toString().split(";");
    }

}