package PiezasLogica.Piezas;

import Mesa.Casilla;
import PiezasLogica.Color_pieza;
import PiezasLogica.Pieza;
import PiezasLogica.Tipo_pieza;

import java.util.ArrayList;
import java.util.List;

public class Caballo extends Pieza {
    private final int[][] movimiento = {
            {-2, -1}, {-2, 1},
            {-1, 2}, {1, 2},
            {2, 1}, {2, -1},
            {1, -2}, {-1, -2}};

    public Caballo(Color_pieza.ColorPieza color) {
        super(color, Tipo_pieza.Pieza_tipo.Caballo);
    }

    @Override
    public List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna) {
        List<Casilla> posiblesMovimientos = new ArrayList<>();
        for (var destino : movimiento) {
            int f = fila + destino[0];
            int c = columna + destino[1];
            if (validarFilaColumna(f, c)) {
                posiblesMovimientos.add(tablero[f][c]);
                if (tablero[f][c].getPieza() != null) {
                    if (!validarEnemigo(tablero[f][c]))
                        posiblesMovimientos.removeLast();
                }
            }
        }
        return posiblesMovimientos;
    }
}
