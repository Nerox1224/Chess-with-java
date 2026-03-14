package PiezasLogica.Piezas;

import Jugador.Player;
import Jugador.TipoPlayer;
import Mesa.Casilla;
import PiezasLogica.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Rey extends Pieza {
    private final int[][] direcciones = {
            {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}
    };
    private final int filaOriginal;

    public Rey(Color_pieza.ColorPieza color, @NotNull Player jugador) {
        super(color, Tipo_pieza.Pieza_tipo.Rey);
        filaOriginal = jugador.getTipoJugador() == TipoPlayer.TipoJugador.PrincipalPlayer ? 7 : 0;
    }

    @Override
    public List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna) {
        List<Casilla> posiblesMovimientos = new ArrayList<>();
        for (var p : direcciones) {
            int fp = fila + p[0];
            int cp = columna + p[1];
            if (validarFilaColumna(fp, cp)) {
                if (tablero[fp][cp].getPieza() != null) {
                    if (validarEnemigo(tablero[fp][cp]))
                        posiblesMovimientos.add(tablero[fp][cp]);
                } else posiblesMovimientos.add(tablero[fp][cp]);
            }
        }
        MovJaque(posiblesMovimientos, tablero);
        PosMovEnrrosque(posiblesMovimientos, tablero);
        return posiblesMovimientos;
    }

    private void MovJaque(@NotNull List<Casilla> posiblesMovimientos, Casilla[][] tablero) {
        List<Casilla> remover = new ArrayList<>();
        int direccion = getColor() == Color_pieza.ColorPieza.blanco ? -1 : 1;
        int[] DerIzq = {1, -1};
        int[][] movimiento = {
                {-2, -1}, {-2, 1},
                {-1, 2}, {1, 2},
                {2, 1}, {2, -1},
                {1, -2}, {-1, -2}};
        int[][] cuadrantes = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
        int[][] horizontales = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for (var posMov : posiblesMovimientos) {
            boolean removido = false;
            //Peon
            int yfo = posMov.getFila() + direccion;
            for (var co : DerIzq) {
                int d = posMov.getColumna() + co;
                if (!validarFilaColumna(yfo, d)) continue;
                if (tablero[yfo][d].getPieza() == null) continue;
                if (!validarEnemigo(tablero[yfo][d])) continue;
                if (tablero[yfo][d].getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Peon) continue;
                remover.add(posMov);
                removido = true;
            }
            if (removido) continue;

            //Caballo
            for (var destino : movimiento) {
                int f = posMov.getFila() + destino[1];
                int c = posMov.getColumna() + destino[0];
                if (!validarFilaColumna(f, c)) continue;
                if (tablero[f][c].getPieza() == null) continue;
                if (!validarEnemigo(tablero[f][c])) continue;
                if (tablero[f][c].getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Caballo) continue;
                remover.add(posMov);
                removido = true;
            }
            if (removido) continue;

            //Torre o Reina
            for (var dir : horizontales) {
                int fp = posMov.getFila() + dir[0];
                int cp = posMov.getColumna() + dir[1];
                while (validarFilaColumna(fp, cp)) {
                    Casilla c = tablero[fp][cp];
                    fp += dir[0];
                    cp += dir[1];
                    if (c.getPieza() == null || c.getPieza().getTipo() == Tipo_pieza.Pieza_tipo.Rey) continue;
                    if (!validarEnemigo(c)) break;
                    if (c.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Torre &&
                            c.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Reina) break;
                    remover.add(posMov);
                    removido = true;
                    break;
                }
            }
            if (removido) continue;

            // Alfil o Reina
            for (var cu : cuadrantes) {
                int f = posMov.getFila() + cu[0];
                int c = posMov.getColumna() + cu[1];
                while (validarFilaColumna(f, c)) {
                    Casilla casilla = tablero[f][c];
                    f += cu[0];
                    c += cu[1];
                    if (casilla.getPieza() == null) continue;
                    if (!validarEnemigo(casilla)) break;
                    if (casilla.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Alfil &&
                            casilla.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Reina) break;
                    remover.add(posMov);
                    break;
                }
            }
        }
        for (var re : remover) {
            posiblesMovimientos.remove(re);
        }
    }

    private void PosMovEnrrosque(List<Casilla> posiblesMovimientos, Casilla[][] tablero) {
        if (SeMovio) return;

        //Derecha
        if (tablero[filaOriginal][5].getPieza() == null &&
                tablero[filaOriginal][6].getPieza() == null &&
                tablero[filaOriginal][7].getPieza() != null) {
            if (tablero[filaOriginal][7].getPieza().getTipo() == Tipo_pieza.Pieza_tipo.Torre
                    && !validarEnemigo(tablero[filaOriginal][7])
                    && tablero[filaOriginal][7].getPieza().getSeMovio())
                posiblesMovimientos.add(tablero[filaOriginal][6]);
        }
        //Izquierda
        if (tablero[filaOriginal][3].getPieza() == null &&
                tablero[filaOriginal][2].getPieza() == null &&
                tablero[filaOriginal][1].getPieza() == null &&
                tablero[filaOriginal][0].getPieza() != null) {
            if (tablero[filaOriginal][0].getPieza().getTipo() == Tipo_pieza.Pieza_tipo.Torre
                    && !validarEnemigo(tablero[filaOriginal][0])
                    && tablero[filaOriginal][0].getPieza().getSeMovio())
                posiblesMovimientos.add(tablero[filaOriginal][1]);
        }
    }

    @Override
    public void Mover(@NotNull List<Casilla> movimientosPosibles, Casilla[][] tablero, Casilla posAnterior, int f, int c) {
        List<Casilla> movimientosEnrrosque = new ArrayList<>();
        PosMovEnrrosque(movimientosEnrrosque, tablero);
        if (tablero[posAnterior.getFila()][posAnterior.getColumna()]
                .getPieza().ValidarMovimiento(movimientosPosibles, tablero, f, c)) {

            tablero[posAnterior.getFila()][posAnterior.getColumna()].getPieza().setSeMovio(true);
            tablero[f][c].setState(tablero[posAnterior.getFila()][posAnterior.getColumna()].getPieza());
            tablero[posAnterior.getFila()][posAnterior.getColumna()].setState(null);

            if(!movimientosEnrrosque.isEmpty()){
                if(c == 1){
                    tablero[filaOriginal][2].setState(tablero[filaOriginal][0].getPieza());
                    tablero[filaOriginal][0].setState(null);
                }
                if(c == 6){
                    tablero[filaOriginal][5].setState(tablero[filaOriginal][7].getPieza());
                    tablero[filaOriginal][7].setState(null);
                }
            }
            SeMovio = true;

        }
    }

    @Override
    public boolean SalvarRey(Casilla[][] tablero, int fila, int columna){
        /*List<Casilla> remover = new ArrayList<>();
        int direccion = getColor() == Color_pieza.ColorPieza.blanco ? -1 : 1;
        int[] DerIzq = {1, -1};
        int[][] movimiento = {
                {-2, -1}, {-2, 1},
                {-1, 2}, {1, 2},
                {2, 1}, {2, -1},
                {1, -2}, {-1, -2}};
        int[][] cuadrantes = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
        int[][] horizontales = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for (var posMov : posiblesMovimientos) {
            boolean removido = false;
            //Peon
            int yfo = posMov.getFila() + direccion;
            for (var co : DerIzq) {
                int d = posMov.getColumna() + co;
                if (!validarFilaColumna(yfo, d)) continue;
                if (tablero[yfo][d].getPieza() == null) continue;
                if (!validarEnemigo(tablero[yfo][d])) continue;
                if (tablero[yfo][d].getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Peon) continue;
                remover.add(posMov);
                removido = true;
            }
            if (removido) continue;

            //Caballo
            for (var destino : movimiento) {
                int f = posMov.getFila() + destino[1];
                int c = posMov.getColumna() + destino[0];
                if (!validarFilaColumna(f, c)) continue;
                if (tablero[f][c].getPieza() == null) continue;
                if (!validarEnemigo(tablero[f][c])) continue;
                if (tablero[f][c].getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Caballo) continue;
                remover.add(posMov);
                removido = true;
            }
            if (removido) continue;

            //Torre o Reina
            for (var dir : horizontales) {
                int fp = posMov.getFila() + dir[0];
                int cp = posMov.getColumna() + dir[1];
                while (validarFilaColumna(fp, cp)) {
                    Casilla c = tablero[fp][cp];
                    fp += dir[0];
                    cp += dir[1];
                    if (c.getPieza() == null || c.getPieza().getTipo() == Tipo_pieza.Pieza_tipo.Rey) continue;
                    if (!validarEnemigo(c)) break;
                    if (c.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Torre &&
                            c.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Reina) break;
                    remover.add(posMov);
                    removido = true;
                    break;
                }
            }
            if (removido) continue;

            // Alfil o Reina
            for (var cu : cuadrantes) {
                int f = posMov.getFila() + cu[0];
                int c = posMov.getColumna() + cu[1];
                while (validarFilaColumna(f, c)) {
                    Casilla casilla = tablero[f][c];
                    f += cu[0];
                    c += cu[1];
                    if (casilla.getPieza() == null) continue;
                    if (!validarEnemigo(casilla)) break;
                    if (casilla.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Alfil &&
                            casilla.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Reina) break;
                    remover.add(posMov);
                    break;
                }
            }
        }*/
        return false;
    }
}
