class Square extends Expression {
    private Expression expression;

    public Square(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate() {
        int x = expression.evaluate ();
        return (int) x * x;
    }

    @Override
    public String toString() {
        return "(" + expression + ")^2";
    }

    public Expression expression() {
        return expression;
    }
}