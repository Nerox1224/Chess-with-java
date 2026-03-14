package Mesa;

import Jugador.Player;
import PiezasLogica.Color_pieza;
import PiezasLogica.Piezas.*;
import PiezasLogica.Tipo_pieza;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Tablero {
    private final Random rand = new Random();

    private final Player J1;
    private final Player J2;

    private Color_pieza.ColorPieza Turno;
    private int numRand = rand.nextInt(1, 3);

    private final Casilla[][] tablero = new Casilla[8][8];
    private Casilla posAnterior;
    private JPanel tabla;
    private List<Casilla> posiblesMovimientos;

    public Tablero(Player J1, Player J2) {
        this.J1 = J1;
        this.J2 = J2;
        NewGAME();
    }

    public JPanel getTablero() {
        return tabla;
    }

    public void PresentGame() {
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/Imagenes/piezas_img/Peon_" + Turno + ".png")));
        Image imagen = icon.getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH);

        JLabel presentGame = new JLabel("Turno del equipo: " + Turno + "\n", new ImageIcon(imagen), SwingConstants.CENTER);
        presentGame.setHorizontalTextPosition(JLabel.CENTER);
        presentGame.setVerticalTextPosition(JLabel.NORTH);
        JOptionPane.showMessageDialog(null, presentGame);
    }

    /*Funcionamiento interno*/
    private void NewGAME() {
        RandomTurno();
        CreateTablero();
        PositionPiezas(7, J1);
        PositionPiezas(0, J2);
    }

    /*Turno del jugador*/
    private void RandomTurno() {
        Turno = numRand == 1 ? J1.getColor() : J2.getColor();
    }

    /*Crear tablero de juego*/
    private void CreateTablero() {
        tabla = new JPanel(new GridLayout(8, 8));
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                tablero[fila][columna] = new Casilla(fila, columna);
                int f = fila;
                int c = columna;

                tablero[fila][columna].setBackground((fila + columna) % 2 == 0 ? Color.WHITE : Color.GRAY);

                tablero[fila][columna].addActionListener(e -> {
                    if (tablero[f][c].getPieza() != null && tablero[f][c].getPieza().getColor() == Turno)
                        SelectCasilla(f, c);
                    else if (posAnterior != null)
                        MovePieza(f, c);
                });
                tabla.add(tablero[fila][columna]);
            }
        }
    }

    /* Posicionar piezas */
    private List<Integer> pos = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7));

    private void PositionPiezas(int fila, Player player) {
        Color_pieza.ColorPieza color = player.getColor();
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
        tablero[fila][pos.get(4)].setState(new Rey(color, player));

        /* ---- Peones ---- */
        int filaPeones;
        if (fila == 0) filaPeones = fila + 1;
        else filaPeones = fila - 1;
        for (var column : pos) {
            tablero[filaPeones][column].setState(new Peon(color, player));
        }
    }


    //Selección de una pieza
    private void SelectCasilla(int fila, int columna) {
        Repintar();

        var list = tablero[fila][columna].getPieza().movimientosPosibles(tablero, fila, columna);
        posiblesMovimientos = list;
        for (var p : list) {
            tablero[p.getFila()][p.getColumna()].setBackground(new Color(176, 235, 245));
        }
        posAnterior = new Casilla(fila, columna);
    }

    //movimiento
    private void MovePieza(int fila, int column) {
        Casilla c = tablero[posAnterior.getFila()][posAnterior.getColumna()];
        if (c.getPieza().ValidarMovimiento(posiblesMovimientos, tablero, fila, column)) {

            /*mover pieza*/
            c.getPieza().Mover(posiblesMovimientos, tablero, posAnterior, fila, column);
            Repintar();

            //moveRey
            if (tablero[fila][column].getPieza().getTipo() == Tipo_pieza.Pieza_tipo.Rey) {
                if (J1.getColor() == Turno) {
                    J1.setPositon_Rey(fila, column);
                } else {
                    J2.setPositon_Rey(fila, column);
                }
            }

            /*Estado actual de la selección*/
            posAnterior = null;
            posiblesMovimientos = null;

            /*Cambio de turno*/
            Turno = tablero[fila][column].getPieza().getColor() == J1.getColor() ?
                    J2.getColor() : J1.getColor();

            /*Rey capturado??*/
            if (PlayerWinner()) BlockTablero();

            /*Verificar sí el rey enemigo esta en jaque*/
            var jaque = tablero[fila][column].getPieza().movimientosPosibles(tablero, fila, column);
            for (var cj : jaque) {
                if (cj.getPieza() == null) continue;
                if (cj.getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Rey) continue;
                if (cj.getPieza().getColor() != Turno) continue;
                JOptionPane.showMessageDialog(null, cj.getPieza().getTipo() + " " +
                        cj.getPieza().getColor() + " en jaque");
            }
        }
    }

    //Validación del ganador del jugador
    private boolean PlayerWinner() {
        if (J1.getColor() == Turno) {
            if (tablero[J1.getFila_Rey()][J1.getColumna_Rey()].getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Rey) {
                JOptionPane.showMessageDialog(null, "El jugador 1 perdió \n Equipo " + J2.getColor() +
                        " gana");
                return true;
            }
        } else if (tablero[J2.getFila_Rey()][J2.getColumna_Rey()].getPieza().getTipo() != Tipo_pieza.Pieza_tipo.Rey) {
            JOptionPane.showMessageDialog(null, "El jugador 2 perdió \n Equipo " + J1.getColor() +
                    " gana");
            return true;
        }
        return false;
    }

    private void BlockTablero() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tablero[i][j].setEnabled(false);
            }
        }
    }

    //repintar las casillas
    private void Repintar() {
        if (posiblesMovimientos == null) return;
        for (var c : posiblesMovimientos) {
            int i = c.getFila();
            int j = c.getColumna();
            if ((i + j) % 2 == 0) {
                tablero[i][j].setBackground(Color.WHITE);
            } else {
                tablero[i][j].setBackground(Color.GRAY);
            }
        }
    }
}
