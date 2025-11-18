public class NodeIf extends Node {
    private NodeBoolExpr boolExpr;
    private NodeStmt thenStmt, elseStmt;

    public NodeIf(NodeBoolExpr boolExpr, NodeStmt thenStmt, NodeStmt elseStmt) {
        this.boolExpr = boolExpr;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    public double eval(Environment env) throws EvalException {
        double cond = boolExpr.eval(env);
        if (cond != 0)
            return thenStmt.eval(env);
        else if (elseStmt != null)
            return elseStmt.eval(env);
        return 0;
    }

    public String code() {
        String c = "if " + boolExpr.code() + " " + thenStmt.code();
        if (elseStmt != null)
            c += " else " + elseStmt.code();
        return c;
    }
}
