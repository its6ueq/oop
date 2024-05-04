abstract class BinaryExpression extends Expression {
    protected Expression left;
    protected Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract String operator ();

    protected abstract int evaluate (int leftValue, int rightValue);

    public Expression left() {
        return left;
    }

    public Expression right() {
        return right;
    }

    @Override
    public String toString () {
        return "(" + left + " " + operator() + " " + right + ")";
    }

    @Override
    public int evaluate() {
        int leftValue = left.evaluate();
        int rightValue = right.evaluate();
        return evaluate(leftValue, rightValue);
    }
}