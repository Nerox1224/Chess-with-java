package Mesa;

import PiezasLogica.Pieza;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Casilla extends JButton {
    private final int fila;
    private final int columna;
    private Pieza pieza; // libre : true    ocupada : false

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    private void ObtainIcon(){
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass()
                        .getResource("/Imagenes/piezas_img/" + pieza.getTipo() + "_" + pieza.getColor() + ".png")));
        Image imagen = icon.getImage().getScaledInstance(40, 50, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(imagen));
    }
    public void setState (Pieza pieza) {
        this.pieza = pieza;
        if(this.pieza == null) {
            setIcon(null);
        }
        else {
            ObtainIcon();
        }
    }
    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }
    public Pieza getPieza() {
        return pieza;
    }
}
