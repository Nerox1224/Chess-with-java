package PiezasLogica.Piezas;

import Jugador.Player;
import Jugador.TipoPlayer;
import Mesa.Casilla;
import PiezasLogica.Color_pieza;
import PiezasLogica.Pieza;
import PiezasLogica.Promotion;
import PiezasLogica.Tipo_pieza;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Peon extends Pieza {
    private final Player jugador;
    private final int direccion;
    private final int RowPromotion;
    private final int firstMovePeon;
    private boolean ValidFirstMove = true;

    public Peon(Color_pieza.ColorPieza color, Player directionPlayer) {
        super(color, Tipo_pieza.Pieza_tipo.Peon);
        jugador = directionPlayer;
        this.RowPromotion = directionPlayer.getTipoJugador() == TipoPlayer.TipoJugador.PrincipalPlayer ? 0 : 7;
        this.direccion = directionPlayer.getTipoJugador() == TipoPlayer.TipoJugador.PrincipalPlayer ? -1 : 1;
        this.firstMovePeon = directionPlayer.getTipoJugador() == TipoPlayer.TipoJugador.PrincipalPlayer ? -2 : 2;
    }

    @Override
    public List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna) {
        List<Casilla> posiblesMovimientos = new ArrayList<>();

        // movimiento normal y primer movimiento
        int m1 = fila + firstMovePeon;
        int mn = fila + direccion;
        if (validarFilaColumna(mn, columna)) {
            if (tablero[mn][columna].getPieza() == null) {
                if (ValidFirstMove)
                    if (tablero[m1][columna].getPieza() == null)
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

    @Override
    public void Mover(@NotNull List<Casilla> movimientosPosibles, Casilla[][] tablero, Casilla posAnterior, int f, int c) {
        if (tablero[posAnterior.getFila()][posAnterior.getColumna()]
                .getPieza().ValidarMovimiento(movimientosPosibles, tablero, f, c)) {
            tablero[posAnterior.getFila()][posAnterior.getColumna()].getPieza().setSeMovio(true);
            tablero[f][c].setState(tablero[posAnterior.getFila()][posAnterior.getColumna()].getPieza());
            tablero[posAnterior.getFila()][posAnterior.getColumna()].setState(null);

            SeMovio = true;
            ValidFirstMove = false;

            if (f == RowPromotion) {
                Promotion pro = new Promotion(tablero, f, c, jugador);
                pro.setVisible(true);
            }
        }
    }
}
