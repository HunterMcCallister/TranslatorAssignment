public class NodeBlock extends Node {

    private NodeStmt stmt;
    private NodeBlock block;

    public NodeBlock(NodeStmt stmt, NodeBlock block) {
        this.stmt = stmt;
        this.block = block;
    }

    public double eval(Environment env) throws EvalException {
        double result = stmt.eval(env);
        if (block != null)
            result = block.eval(env);
        return result;
    }

    public String code() {
        return stmt.code() + (block == null ? "" : block.code());
    }
}
