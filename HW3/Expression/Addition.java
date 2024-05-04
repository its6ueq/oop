class Addition extends BinaryExpression {
    public Addition(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String operator () {
        return "+";
    }

    @Override
    protected int evaluate(int leftValue, int rightValue) {
        return leftValue + rightValue;
    }

    public Expression left() {
        return super.left;
    }

    public Expression right() {
        return super.right;
    }
}