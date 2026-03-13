package PiezasLogica;

import java.util.List;

import Mesa.*;
import org.jetbrains.annotations.NotNull;

public abstract class Pieza implements Controlador {

    protected Color_pieza.ColorPieza color;
    protected Tipo_pieza.Pieza_tipo tipo;
    protected boolean SeMovio;

    public Pieza(Color_pieza.ColorPieza color, Tipo_pieza.Pieza_tipo tipo) {
        this.color = color;
        this.tipo = tipo;
        SeMovio = false;
    }

    //Metodos abstractos
    public abstract List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna);


    //Controlador
    @Override
    public boolean ValidarMovimiento(@NotNull List<Casilla> movePos, Casilla[][] tablero, int fd, int cd) {
        for (var destino : movePos) {
            if (tablero[fd][cd] == tablero[destino.getFila()][destino.getColumna()])
                return true;
        }
        return false;
    }

    @Override
    public void Mover(@NotNull List<Casilla> movimientosPosibles, Casilla[][] tablero, Casilla posAnterior, int f, int c) {
        if (tablero[posAnterior.getFila()][posAnterior.getColumna()]
                .getPieza().ValidarMovimiento(movimientosPosibles, tablero, f, c)) {
            tablero[posAnterior.getFila()][posAnterior.getColumna()].getPieza().setSeMovio(true);
            tablero[f][c].setState(tablero[posAnterior.getFila()][posAnterior.getColumna()].getPieza());
            tablero[posAnterior.getFila()][posAnterior.getColumna()].setState(null);

            SeMovio = true;
        }
    }


    //Validaciones
    public boolean validarFilaColumna(int fila, int columna) {
        return (fila < 8 && columna < 8 && fila > -1 && columna > -1);
    }

    public boolean validarEnemigo(@NotNull Casilla c) {
        return c.getPieza().getColor() != color;
    }

    //Sets
    public void setSeMovio(boolean seMovio) {
        this.SeMovio = seMovio;
    }


    //Gets
    public Tipo_pieza.Pieza_tipo getTipo() {
        return tipo;
    }

    public Color_pieza.ColorPieza getColor() {
        return color;
    }

    public boolean getSeMovio() {
        return !SeMovio;
    }
}
