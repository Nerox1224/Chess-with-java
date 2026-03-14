package Jugador;

import PiezasLogica.Color_pieza;

import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;
import java.util.List;

public class ConfigurationPlayer extends JPanel {
    private JLabel Title = new JLabel("Configurations", JLabel.CENTER);
    private JComboBox<Color_pieza.ColorPieza> ConfigColor = new JComboBox<>(Color_pieza.ColorPieza.values());
    private JComboBox<TipoPlayer.TipoJugador> ConfigTipo = new JComboBox<>(TipoPlayer.TipoJugador.values());

    public ConfigurationPlayer(Player p1, Player p2) {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.VERTICAL;
        gc.insets = new Insets(5, 5, 5, 5);

        gc.gridx = 1;
        gc.gridy = 0;
        Title.setFont(new Font("Arial",Font.BOLD,32));
        add(Title, gc);
        gc.gridy = 2;
        add(Box.createVerticalStrut(90));
        add(ConfigTipo, gc);
        gc.gridy = 3;
        add(Box.createVerticalStrut(40));
        add(ConfigColor, gc);
        AddConfigurations(p1, p2);
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
