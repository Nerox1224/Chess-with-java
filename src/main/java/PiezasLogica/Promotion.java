package PiezasLogica;

import Jugador.Player;
import Mesa.Casilla;
import PiezasLogica.Piezas.Alfil;
import PiezasLogica.Piezas.Caballo;
import PiezasLogica.Piezas.Reina;
import PiezasLogica.Piezas.Torre;

import javax.swing.*;
import java.awt.*;

public class Promotion extends Dialog {
    public Promotion(Casilla[][] tablero, int fila, int columna, Player jugador) {
        super(null, true);
        setResizable(false);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setUndecorated(true);

        add(promotion(tablero, fila, columna, jugador));
    }

    private JPanel promotion(Casilla[][] tablero, int fila, int columna, Player jugador) {
        JPanel promotion = new JPanel(new GridLayout(1, 4));
        Casilla[][] Desition = new Casilla[1][4];

        for (int i = 0; i < 4; i++) {
            Desition[0][i] = new Casilla(0, i);
            promotion.add(Desition[0][i]);
            int c = i;
            Desition[0][i].addActionListener(e -> {
                tablero[fila][columna].setState(Desition[0][c].getPieza());
                dispose();
            });
        }

        Desition[0][0].setState(new Torre(jugador.getColor()));
        Desition[0][1].setState(new Alfil(jugador.getColor()));
        Desition[0][2].setState(new Caballo(jugador.getColor()));
        Desition[0][3].setState(new Reina(jugador.getColor()));
        return promotion;
    }
}
