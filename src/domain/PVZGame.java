package domain;

import java.io.Serializable;
import java.util.List;

public class PVZGame implements Serializable {
    // Declaración de un arreglo bidimensional para representar el tablero del juego
    private String[][] board;
    private Plant[][] plantas;
    private Zombie[][] zombies;
    private int soles;
    private int puntaje;
    /**
     * Constructor de la clase PVZGame.
     *
     * Este constructor inicializa el tablero de juego con un tamaño de 5 filas y 8 columnas,
     * y establece el puntaje inicial en 0.
     */
    public PVZGame() {
        board = new String[5][8];
        plantas = new Plant[5][8];
        zombies = new Zombie[5][8];
        puntaje = 0;
        soles = 50;
    }

    /**
     * Obtiene el tablero de juego.
     *
     * @return Un arreglo bidimensional de tipo String que representa el estado actual del tablero de juego.
     * Cada celda puede contener el nombre de una planta, un zombi o una cadena vacía.
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * Obtiene el puntaje actual del juego.
     *
     * Este método devuelve el puntaje acumulado hasta el momento en el juego.
     *
     * @return El puntaje actual del juego.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Coloca una planta en una posición específica del tablero.
     *
     * Este método coloca una planta en una celda del tablero si esa celda está vacía
     * (es decir, su valor es `null`) y no se encuentra en las columnas 0 o 7,
     * las cuales están reservadas para la podadora y el spawn de zombis, respectivamente.
     * Si la planta se coloca exitosamente, se aumenta el puntaje en 10 puntos.
     *
     * @param plant El tipo de planta a colocar en el tablero.
     * @param row La fila en la que se desea colocar la planta.
     * @param col La columna en la que se desea colocar la planta.
     * @return `true` si la planta se coloca correctamente, `false` si no se pudo colocar.
     */
    public boolean placePlant(String plant, int row, int col) {
        if (board[row][col] == null && col != 0 && col != 7) {
            board[row][col] = plant;

            Plant asignacionNuevaPlanta;
            int[] posicionesArreglo = {row, col};
            switch (plant) {
                case "Sunflower":
                    asignacionNuevaPlanta = new Sunflower(300, "Sunflower", posicionesArreglo);
                    break;
                case "Peashooter":
                    asignacionNuevaPlanta = new PeasShooter(300, "Peashooter", posicionesArreglo);
                    break;
                case "Wall-nut":
                    asignacionNuevaPlanta = new WallNut(4000, "Wall-nut", posicionesArreglo);
                    break;
                default:
                    return false;
            }

            plantas[row][col] = asignacionNuevaPlanta;

            puntaje += 10;

            return true;
        }
        return false;
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

    public Plant[][] getTableroPlantas() {
        return plantas;
    }

    private void imprimirTableros() {
        System.out.println("Estado de Board:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print((board[i][j] == null ? "Empty" : board[i][j]) + "\t");
            }
            System.out.println();
        }

        System.out.println("Estado de Plantas:");
        for (int i = 0; i < plantas.length; i++) {
            for (int j = 0; j < plantas[i].length; j++) {
                if (plantas[i][j] != null) {
                    System.out.print(plantas[i][j].getClass().getSimpleName() + "\t");
                } else {
                    System.out.print("Empty\t");
                }
            }
            System.out.println();
        }
    }

    /**
     * Elimina una planta de una posición específica del tablero.
     *
     * Este método elimina una planta de una celda del tablero si esa celda no está vacía
     * (es decir, si contiene una planta) y si la celda no se encuentra en las columnas 0 o 7,
     * las cuales están reservadas para la podadora y el spawn de zombis, respectivamente.
     * Si la planta se elimina correctamente, se disminuye el puntaje en 5 puntos.
     *
     * @param row La fila de la planta que se desea eliminar.
     * @param col La columna de la planta que se desea eliminar.
     * @return `true` si la planta se elimina correctamente, `false` si no se pudo eliminar
     *         debido a que la celda está vacía o se encuentra en una columna restringida.
     */
    public boolean removePlant(int row, int col) {
        if (board[row][col] != null && col != 0 && col != 7) {
            board[row][col] = null;
            puntaje -= 5;
            return true;
        }
        return false;
    }

    /**
     * Obtiene las posiciones disponibles en el tablero, dependiendo de si se está realizando una eliminación o colocación de plantas.
     *
     * Este método devuelve un array de cadenas que representan las posiciones (fila y columna) en el tablero donde
     * se puede colocar o eliminar una planta. La función se comporta de manera diferente según el parámetro `isRemoval`:
     * - Si `isRemoval` es `true`, busca las posiciones ocupadas por plantas que pueden ser eliminadas (es decir, celdas
     *   que no están vacías).
     * - Si `isRemoval` es `false`, busca las posiciones vacías donde se pueden colocar nuevas plantas, excluyendo las
     *   columnas 0 y 7, que están reservadas para la podadora y el spawn de zombis, respectivamente.
     *
     * @param isRemoval Si es `true`, busca las posiciones con plantas para eliminarlas; si es `false`, busca posiciones vacías
     *                  para colocar nuevas plantas.
     * @return Un array de cadenas con las posiciones disponibles, en formato "Fila X, Columna Y".
     */
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