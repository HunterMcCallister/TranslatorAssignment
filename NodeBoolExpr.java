public class NodeBoolExpr extends Node {
    private NodeExpr expr1, expr2;
    private String relop;

    public NodeBoolExpr(NodeExpr expr1, String relop, NodeExpr expr2) {
        this.expr1 = expr1;
        this.relop = relop;
        this.expr2 = expr2;
    }

    public double eval(Environment env) throws EvalException {
        double left = expr1.eval(env);
        double right = expr2.eval(env);
        switch (relop) {
            case "<":  return left < right ? 1 : 0;
            case "<=": return left <= right ? 1 : 0;
            case ">":  return left > right ? 1 : 0;
            case ">=": return left >= right ? 1 : 0;
            case "==": return left == right ? 1 : 0;
            case "<>": return left != right ? 1 : 0;
            default: throw new EvalException(0, "invalid relop: " + relop);
        }
    }

    public String code() {
        return "(" + expr1.code() + " " + relop + " " + expr2.code() + ")";
    }
}
