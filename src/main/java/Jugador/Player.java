package Jugador;

import PiezasLogica.Color_pieza;

public class Player {
    private TipoPlayer.TipoJugador tipoJugador;
    private Color_pieza.ColorPieza color; //equipo

    public Player(Color_pieza.ColorPieza color, TipoPlayer.TipoJugador tipo) {
        this.color = color;
        this.tipoJugador = tipo;
    }

    // Sets
    public void setTipoJugador(TipoPlayer.TipoJugador tipoJugador) {
        this.tipoJugador = tipoJugador;
    }

    public void setColor(Color_pieza.ColorPieza color) {
        this.color = color;
    }

    // Gets
    public TipoPlayer.TipoJugador getTipoJugador() {
        return tipoJugador;
    }

    public Color_pieza.ColorPieza getColor() {
        return color;
    }
}
