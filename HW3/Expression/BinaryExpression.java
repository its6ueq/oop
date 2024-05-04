abstract class BinaryExpression implements Expression {
    Expression left;
    Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    abstract String operator ();

    abstract int evaluate (int leftValue, int rightValue);

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