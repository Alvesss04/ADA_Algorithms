public enum Direction {
    N(-1, 0),
    S(1, 0),
    E(0, 1),
    W(0, -1);

    public final int dr;
    public final int dc;

    Direction(int directionRow, int directionColumn) {
        this.dr = directionRow;
        this.dc = directionColumn;
    }

    public static Direction deLetra(char letra) {
        switch (letra) {
            case 'N': return N;
            case 'S': return S;
            case 'E': return E;
            case 'W': return W;
            default: throw new IllegalArgumentException("Direção inválida: " + letra);
        }
    }
}