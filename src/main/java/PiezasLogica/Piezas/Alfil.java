package PiezasLogica.Piezas;

import Mesa.Casilla;
import PiezasLogica.Color_pieza;
import PiezasLogica.Pieza;
import PiezasLogica.Tipo_pieza;

import java.util.ArrayList;
import java.util.List;

public class Alfil extends Pieza {
    private final int[][] cuadrantes = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

    public Alfil(Color_pieza.ColorPieza color) {
        super(color, Tipo_pieza.Pieza_tipo.Alfil);
    }

    @Override
    public List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna) {
        List<Casilla> posiblesMovimientos = new ArrayList<>();
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
        return posiblesMovimientos;
    }
}
