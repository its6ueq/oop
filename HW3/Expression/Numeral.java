class Numeral implements Expression {
    private int value;

    public Numeral(int value) {
        this.value = value;
    }

    @Override
    public int evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public int value() {
        return value;
    }
}