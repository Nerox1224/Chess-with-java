package PiezasLogica;

import Mesa.Casilla;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Controlador {
    boolean ValidarMovimiento(List<Casilla> movePos, Casilla[][] tablero, int fd, int cd);
    boolean SalvarRey(Casilla[][] tablero, int fila, int columna);

    List<Casilla> movimientosPosibles(Casilla[][] tablero, int fila, int columna);

    void Mover(@NotNull List<Casilla> movimientosPosibles, Casilla[][] tablero, Casilla posAnterior, int f, int c);
}