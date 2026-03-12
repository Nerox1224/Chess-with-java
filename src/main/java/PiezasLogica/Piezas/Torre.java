package PiezasLogica.Piezas;

import Mesa.Casilla;
import PiezasLogica.Color_pieza;
import PiezasLogica.Pieza;
import PiezasLogica.Tipo_pieza;

import java.util.ArrayList;
import java.util.List;

public class Torre extends Pieza {
    //Direcciones
    private final int[][] direction = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public Torre(Color_pieza.ColorPieza color) {
        super(color, Tipo_pieza.Pieza_tipo.Torre);
    }

    @Override
    public List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna) {
        List<Casilla> posiblesMovimientos = new ArrayList<>();

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

    public void Jaque() {

    }
}
