package ChessBot;

import Jugador.Player;
import Jugador.TipoPlayer;
import Mesa.Casilla;
import PiezasLogica.Color_pieza;
import PiezasLogica.Pieza;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Bot extends Player {

    public Bot(Color_pieza.ColorPieza color, TipoPlayer.TipoJugador tipo) {
        super(color, tipo);
    }

    /*  Comportamiento del Bot
     -  Obtener las piezas
     -  Obtener los movimientos posibles de cada pieza
     -  Elegir el mejor movimiento
     -  Seleccionar la pieza
     -  Mover pieza
    */


    public Movimiento BetterMove(@NotNull Casilla[][] tablero) {
        int BetterValue = Integer.MIN_VALUE;
        Movimiento better = null;
        List<Movimiento> moves = ObtainPiceAndMoves(tablero, getColor());

        if (moves.isEmpty())
            return null;

        for (var m : moves) {
            Pieza MovePieza = hacerMovimiento(tablero, m);

            int value = MiniMax(tablero, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            deshacerMovimiento(tablero, m, MovePieza);

            if (value > BetterValue) {
                BetterValue = value;
                better = m;
            }
        }

        //better = moves.get(r.nextInt(moves.size()));
        return better;
    }

    private List<Movimiento> ObtainPiceAndMoves(@NotNull Casilla[][] tablero, Color_pieza.ColorPieza color) {
        List<Movimiento> moves = new ArrayList<>();

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                Casilla c = tablero[i][j];
                if (c.getPieza() == null) continue;
                if (c.getPieza().getColor() != color) continue;
                for (var m : c.getPieza().movimientosPosibles(tablero, i, j))
                    moves.add(new Movimiento(i, j, m.getFila(), m.getColumna()));
            }
        }
        return moves;
    }

    private int MiniMax(Casilla[][] tablero, int Deep, int alpha, int beta, boolean maximizando) {
        if (Deep == 0)
            return evaluar(tablero);

        Color_pieza.ColorPieza color = maximizando ? getColor() :
                getColor() == Color_pieza.ColorPieza.blanco ? Color_pieza.ColorPieza.negro : Color_pieza.ColorPieza.blanco;

        List<Movimiento> moves = ObtainPiceAndMoves(tablero, color);

        if (moves.isEmpty()) {
            return evaluar(tablero);
        }

        if (maximizando) {

            int maxEval = Integer.MIN_VALUE;

            for (Movimiento m : moves) {

                Pieza capturada = hacerMovimiento(tablero, m);

                int eval = MiniMax(tablero, Deep - 1, alpha, beta, false);

                deshacerMovimiento(tablero, m, capturada);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha) break;
            }

            return maxEval;

        } else {

            int minEval = Integer.MAX_VALUE;

            for (Movimiento m : moves) {

                Pieza capturada = hacerMovimiento(tablero, m);

                int eval = MiniMax(tablero, Deep - 1, alpha, beta, true);

                deshacerMovimiento(tablero, m, capturada);

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                if (beta <= alpha) break;
            }

            return minEval;
        }
    }

    public int evaluar(Casilla[][] tablero) {

        int valor = 0;

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {

                Pieza p = tablero[f][c].getPieza();

                if (p == null) continue;

                int v = switch (p.getTipo()) {
                    case Peon -> 1;
                    case Caballo, Alfil -> 3;
                    case Torre -> 5;
                    case Reina -> 9;
                    case Rey -> 100;
                };

                if (p.getColor() == getColor())
                    valor += v;
                else
                    valor -= v;
            }
        }
        return valor;
    }

    private Pieza hacerMovimiento(@NotNull Casilla[][] tablero, @NotNull Movimiento m) {

        Pieza origen = tablero[m.getFo()][m.getCo()].getPieza();
        Pieza destino = tablero[m.getFd()][m.getCd()].getPieza();

        tablero[m.getFd()][m.getCd()].setState(origen);
        tablero[m.getFo()][m.getCo()].setState(null);

        return destino;
    }

    private void deshacerMovimiento(@NotNull Casilla[][] tablero, @NotNull Movimiento m, Pieza capturada) {

        Pieza pieza = tablero[m.getFd()][m.getCd()].getPieza();

        tablero[m.getFo()][m.getCo()].setState(pieza);
        tablero[m.getFd()][m.getCd()].setState(capturada);
    }
}