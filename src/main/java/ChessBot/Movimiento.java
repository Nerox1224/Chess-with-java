package ChessBot;

public class Movimiento {
    private final int fo, co;
    private final int fd, cd;

    public Movimiento(int fo, int co, int fd, int cd) {
        this.fo = fo;
        this.co = co;
        this.fd = fd;
        this.cd = cd;
    }

    public int getFo() {
        return fo;
    }

    public int getCo() {
        return co;
    }

    public int getFd() {
        return fd;
    }

    public int getCd() {
        return cd;
    }
}
