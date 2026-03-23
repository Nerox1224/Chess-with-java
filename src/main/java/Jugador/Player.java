package Jugador;

import PiezasLogica.Color_pieza;

public class Player {
    private TipoPlayer.TipoJugador tipoJugador;
    private Color_pieza.ColorPieza color; //equipo
    private int Fila_Rey;
    private int Columna_Rey;

    public Player(Color_pieza.ColorPieza color, TipoPlayer.TipoJugador tipo) {
        this.color = color;
        this.tipoJugador = tipo;
        if (tipoJugador == TipoPlayer.TipoJugador.PrincipalPlayer)
            setPositon_Rey(7, 4);
        else
            setPositon_Rey(0, 4);
    }

    // Sets
    public void setTipoJugador(TipoPlayer.TipoJugador tipoJugador) {

        this.tipoJugador = tipoJugador;
    }

    public void setColor(Color_pieza.ColorPieza color) {
        this.color = color;
    }

    public void setPositon_Rey(int Fila_Rey, int Columna_Rey) {
        this.Fila_Rey = Fila_Rey;
        this.Columna_Rey = Columna_Rey;
    }

    // Gets
    public TipoPlayer.TipoJugador getTipoJugador() {
        return tipoJugador;
    }

    public Color_pieza.ColorPieza getColor() {
        return color;
    }

    public int getFila_Rey() {
        return Fila_Rey;
    }

    public int getColumna_Rey() {
        return Columna_Rey;
    }
}
