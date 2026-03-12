package PiezasLogica.Piezas;

import Mesa.Casilla;
import PiezasLogica.Color_pieza;
import PiezasLogica.Pieza;
import PiezasLogica.Tipo_pieza;

import java.util.ArrayList;
import java.util.List;

public class Reina extends Pieza {
    //tipo torre
    private int[][] cuadrantes = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
    //tipo alfil
    private int[][] direction = {
            {-1, 0}, // up
            {0, 1},  //rigth
            {1, 0},  //down
            {0, -1}  //left
    };
    ;

    public Reina(Color_pieza.ColorPieza color) {
        super(color, Tipo_pieza.Pieza_tipo.Reina);
    }

    @Override
    public List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna) {
        List<Casilla> posiblesMovimientos = new ArrayList<>();
        // movimiento tipo Alfil
        for (var p : cuadrantes) {
            int f = fila + p[0];
            int c = columna + p[1];
            while (validarFilaColumna(f, c)) {
                if (tablero[f][c].getPieza() != null) {
                    if (validarEnemigo(tablero[f][c]))
                        posiblesMovimientos.add(tablero[f][c]);
                    break;
                } else posiblesMovimientos.add(tablero[f][c]);
                f += p[0];
                c += p[1];
            }
        }

        // movimiento tipo Torre
        for (var dir : direction) {
            int fp = fila + dir[0];
            int cp = columna + dir[1];
            while (validarFilaColumna(fp, cp)) {
                Casilla c = tablero[fp][cp];
                posiblesMovimientos.add(tablero[fp][cp]);
                if (c.getPieza() != null) {
                    if (!validarEnemigo(c))
                        posiblesMovimientos.removeLast();
                    break;
                }
                fp += dir[0];
                cp += dir[1];
            }
        }
        return posiblesMovimientos;
    }
}
