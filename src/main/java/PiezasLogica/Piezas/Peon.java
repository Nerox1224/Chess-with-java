package PiezasLogica.Piezas;

import Mesa.Casilla;
import PiezasLogica.Color_pieza;
import PiezasLogica.Pieza;
import PiezasLogica.Tipo_pieza;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Peon extends Pieza {
    private int direccion;
    private int firstMovePeon;
    private boolean ValidFirstMove = true;

    public Peon(Color_pieza.ColorPieza color) {
        super(color, Tipo_pieza.Pieza_tipo.Peon);
        this.direccion = getColor() == Color_pieza.ColorPieza.blanco ? -1 : 1;
        this.firstMovePeon = getColor() == Color_pieza.ColorPieza.blanco ? -2 : 2;
    }

    @Override
    public List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna) {
        List<Casilla> posiblesMovimientos = new ArrayList<>();

        // movimiento normal y primer movimiento
        int m1 = fila + firstMovePeon;
        int mn = fila + direccion;
        if (validarFilaColumna(mn, columna)) {
            if (tablero[mn][columna].getPieza() == null) {
                if (tablero[m1][columna].getPieza() == null && ValidFirstMove)
                    posiblesMovimientos.add(tablero[m1][columna]);
                posiblesMovimientos.add(tablero[mn][columna]);
            }
        }
        //Ataque
        int[] DerIzq = {1, -1};
        int yfo = fila + direccion;
        for (var co : DerIzq) {
            int d = columna + co;
            if (!validarFilaColumna(yfo, d)) continue;
            if (tablero[yfo][d].getPieza() == null) continue;
            if (!validarEnemigo(tablero[yfo][d])) continue;
            posiblesMovimientos.add(tablero[yfo][d]);
        }
        return posiblesMovimientos;
    }
}
