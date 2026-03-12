package Mesa;

import Jugador.Player;
import PiezasLogica.Color_pieza;
import PiezasLogica.Piezas.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero {
    private final Random rand = new Random();

    private final Player J1;
    private final Player J2;

    private Color_pieza.ColorPieza Turno;
    private int numRand = rand.nextInt(1, 3);

    private Casilla[][] tablero = new Casilla[8][8];
    private Casilla posAnterior;
    private JPanel tabla;
    private List<Casilla> posiblesMovimientos;

    public Tablero(Player J1, Player J2) {
        CreateTablero();
        PositionPiezas(0, Color_pieza.ColorPieza.negro);
        PositionPiezas(7, Color_pieza.ColorPieza.blanco);
        this.J1 = J1;
        this.J2 = J2;
        Turno = numRand == 1 ? J1.getColor() : J2.getColor();
    }

    public JPanel getTablero() {
        return tabla;
    }

    public Color_pieza.ColorPieza getTurno() {
        return Turno;
    }

    private void CreateTablero() {
        tabla = new JPanel(new GridLayout(8, 8));
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                tablero[fila][columna] = new Casilla(fila, columna);
                int f = fila;
                int c = columna;

                tablero[fila][columna].setBackground((fila + columna) % 2 == 0 ? Color.WHITE : Color.GRAY);

                tablero[fila][columna].addActionListener(e -> {
                    if (tablero[f][c].getPieza() != null)
                        SelectCasilla(f, c);
                    if (posAnterior != null && tablero[f][c] != tablero[posAnterior.getFila()][posAnterior.getColumna()])
                        MovePieza(f, c);
                });
                tabla.add(tablero[fila][columna]);
            }
        }
    }

    void JaqueRey() {

    }

    //Posicionar piezas
    private List<Integer> pos = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7));

    public void PositionPiezas(int fila, Color_pieza.ColorPieza color) {
        /* ---- Torres ---- */
        tablero[fila][pos.get(0)].setState(new Torre(color));
        tablero[fila][pos.get(7)].setState(new Torre(color));

        /* ---- Caballos ---- */
        tablero[fila][pos.get(1)].setState(new Caballo(color));
        tablero[fila][pos.get(6)].setState(new Caballo(color));

        /* ---- Alfil ---- */
        tablero[fila][pos.get(2)].setState(new Alfil(color));
        tablero[fila][pos.get(5)].setState(new Alfil(color));

        /* ---- Reinas ---- */
        tablero[fila][pos.get(3)].setState(new Reina(color));

        /* ---- Reyes ---- */
        tablero[fila][pos.get(4)].setState(new Rey(color));

        /* ---- Peones ---- */
        int filaPeones;
        if (fila == 0) filaPeones = fila + 1;
        else filaPeones = fila - 1;
        for (var column : pos) {
            tablero[filaPeones][column].setState(new Peon(color));
        }
    }

    private void SelectCasilla(int fila, int columna) {
        Repintar();
        if (tablero[fila][columna].getPieza().getColor() != Turno) {
            JOptionPane.showMessageDialog(null, "Turno del equipo: " + Turno);
            return;
        }
        var list = tablero[fila][columna].getPieza().movimientosPosibles(tablero, fila, columna);
        posiblesMovimientos = list;
        for (var p : list) {
            tablero[p.getFila()][p.getColumna()].setBackground(new Color(176, 235, 245));
        }
        posAnterior = new Casilla(fila, columna);
    }

    private void MovePieza(int fila, int column) {
        Casilla c = tablero[posAnterior.getFila()][posAnterior.getColumna()];
        if (c.getPieza().ValidarMovimiento(posiblesMovimientos, tablero, fila, column)) {
            c.getPieza().Mover(posiblesMovimientos, tablero, posAnterior, fila, column);
            Repintar();

            posAnterior = null;
            posiblesMovimientos = null;

            Turno = tablero[fila][column].getPieza().getColor() == J1.getColor() ?
                    J2.getColor() : J1.getColor();
        }
    }

    private void Repintar() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0)
                    tablero[i][j].setBackground(Color.WHITE);
                else
                    tablero[i][j].setBackground(Color.GRAY);
            }
        }
    }
}
