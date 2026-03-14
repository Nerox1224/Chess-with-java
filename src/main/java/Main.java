import Jugador.ConfigurationPlayer;
import Jugador.Player;
import Jugador.TipoPlayer;
import Mesa.Tablero;
import PiezasLogica.Color_pieza;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;


public class Main extends JFrame {
    private final Player PJugador = new Player(Color_pieza.ColorPieza.blanco, TipoPlayer.TipoJugador.PrincipalPlayer);
    private final Player SJugador = new Player(Color_pieza.ColorPieza.negro, TipoPlayer.TipoJugador.player);
    private ConfigurationPlayer Config = new ConfigurationPlayer(PJugador, SJugador);
    private Tablero tablero = new Tablero(PJugador, SJugador);

    private JPanel container;
    private CardLayout cardLayout;

    static void main(String[] args) {
        new Main().setVisible(true);
    }

    public Main() {
        InitComponents();
    }

    private void InitComponents() {
        setTitle("Ajedrez");
        setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("Imagenes/ChessIcon.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(TitleGame(), "Ajedrez");
        container.add(Tb(), "Game");
        container.add(Cf(), "Configuration");
        add(container);
    }


    private @NotNull JPanel TitleGame() {
        JPanel TitleGame = new JPanel(new BorderLayout());
        JLabel Titulo = new JLabel("Juega Ajedrez", SwingConstants.CENTER);
        TitleGame.add(Titulo, BorderLayout.NORTH);
        TitleGame.add(new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("Imagenes/ChessIcon.png")))), BorderLayout.CENTER
        );
        TitleGame.add(OptionsGame(), BorderLayout.SOUTH);
        return TitleGame;
    }

    private @NotNull JPanel OptionsGame() {
        JPanel Options = new JPanel();
        Options.setLayout(new BoxLayout(Options, BoxLayout.Y_AXIS));

        JButton Newgame = new JButton("New game");
        JButton Continue = new JButton("Continue");
        JButton Configuration = new JButton("Configuration");
        JButton Exit = new JButton("Exit");


        Newgame.setAlignmentX(Component.CENTER_ALIGNMENT);
        Continue.setAlignmentX(Component.CENTER_ALIGNMENT);
        Configuration.setAlignmentX(Component.CENTER_ALIGNMENT);
        Exit.setAlignmentX(Component.CENTER_ALIGNMENT);

        Options.add(Box.createVerticalStrut(50));
        Options.add(Newgame);
        Options.add(Box.createVerticalStrut(20));
        Options.add(Continue);
        Options.add(Box.createVerticalStrut(20));
        Options.add(Configuration);
        Options.add(Box.createVerticalStrut(20));
        Options.add(Exit);
        Options.add(Box.createVerticalStrut(80));

        /*   --------Eventos--------   */
        Newgame.addActionListener(e -> {
            tablero = new Tablero(PJugador, SJugador);
            container.remove(Tb());
            container.add(Tb(), "Game");
            cardLayout.show(container, "Game");
            tablero.PresentGame();
        });
        Continue.addActionListener(e -> {
            cardLayout.show(container, "Game");
            tablero.PresentGame();
        });
        Configuration.addActionListener(e -> {
            cardLayout.show(container, "Configuration");
        });
        Exit.addActionListener(e -> this.dispose());
        return Options;
    }

    /*   --------Tablero--------   */
    private @NotNull JPanel Tb() {
        JPanel Newgame = new JPanel(new BorderLayout());

        JButton Regresar = new JButton("Regresar");
        Regresar.setAlignmentX(Component.LEFT_ALIGNMENT);
        Regresar.addActionListener(e -> cardLayout.show(container, "Ajedrez"));
        Newgame.add(Regresar, BorderLayout.NORTH);
        Newgame.add(tablero.getTablero(), BorderLayout.CENTER);
        return Newgame;
    }

    /*   --------Configuración--------   */
    private @NotNull JPanel Cf() {
        JPanel Configuration = new JPanel(new BorderLayout());

        JButton Regresar = new JButton("Regresar");
        Regresar.setAlignmentX(Component.LEFT_ALIGNMENT);
        Regresar.addActionListener(e -> cardLayout.show(container, "Ajedrez"));
        Configuration.add(Regresar, BorderLayout.NORTH);
        Configuration.add(Config, BorderLayout.CENTER);
        return Configuration;
    }
}