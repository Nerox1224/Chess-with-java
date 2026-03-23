package Jugador;

import ChessBot.Bot;
import PiezasLogica.Color_pieza;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPlayer extends JPanel {
    private final JComboBox<Color_pieza.ColorPieza> ConfigColor = new JComboBox<>(Color_pieza.ColorPieza.values());
    private final JComboBox<TipoPlayer.TipoJugador> ConfigTipo = new JComboBox<>(TipoPlayer.TipoJugador.values());

    public ConfigurationPlayer(Player p1, Player p2) {
        setLayout(new BorderLayout());
        add(Titulo(), BorderLayout.NORTH);
        add(ButtonsConfig(p1, p2), BorderLayout.CENTER);
    }

    /*  --- Add Title --- */
    private JPanel Titulo() {
        JPanel Titulo = new JPanel(new BorderLayout());
        JLabel Title = new JLabel("Configurations", JLabel.CENTER);
        Title.setFont(new Font("Arial", Font.BOLD, 32));
        Titulo.add(Box.createVerticalStrut(90));
        Titulo.add(Title, BorderLayout.SOUTH);
        return Titulo;
    }

    /* --- Add Buttons --- */
    private JPanel ButtonsConfig(Player p1, Player p2) {
        JPanel ButtonsConfig = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.VERTICAL;
        gc.insets = new Insets(5, 5, 5, 5);

        gc.gridx = 0;
        gc.gridy = 0;
        ButtonsConfig.add(new JLabel("Enfrentar a : "), gc);
        gc.gridy = 1;
        ButtonsConfig.add(new JLabel("Selecciona tu equipo : "), gc);

        gc.gridx = 1;
        gc.gridy = 0;
        ButtonsConfig.add(ConfigTipo, gc);
        gc.gridy = 1;
        ButtonsConfig.add(Box.createVerticalStrut(40));
        ButtonsConfig.add(ConfigColor, gc);
        AddConfigurations(p1, p2);
        return ButtonsConfig;
    }

    private void AddConfigurations(Player p1, Player p2) {
        ConfigTipo.removeItemAt(0);
        ConfigTipo.addActionListener(e -> {
            p2.setTipoJugador((TipoPlayer.TipoJugador) ConfigTipo.getSelectedItem());
        });
        ConfigColor.addActionListener(e -> {
            p1.setColor((Color_pieza.ColorPieza) ConfigColor.getSelectedItem());
            p2.setColor(p1.getColor() == Color_pieza.ColorPieza.blanco ? Color_pieza.ColorPieza.negro : Color_pieza.ColorPieza.blanco);
        });

    }
}
