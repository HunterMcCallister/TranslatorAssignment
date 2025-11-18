public class NodeWhile extends Node {
    private NodeBoolExpr boolExpr;
    private NodeStmt stmt;

    public NodeWhile(NodeBoolExpr boolExpr, NodeStmt stmt) {
        this.boolExpr = boolExpr;
        this.stmt = stmt;
    }

    public double eval(Environment env) throws EvalException {
        double result = 0;
        while (boolExpr.eval(env) != 0)
            result = stmt.eval(env);
        return result;
    }

    public String code() {
        return "while " + boolExpr.code() + " " + stmt.code();
    }
}
