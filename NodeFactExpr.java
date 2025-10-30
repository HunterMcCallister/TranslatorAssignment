/**
 * Represents a parenthesized expression node in the parse tree.
 *
 * Wraps a sub expression in parentheses and delegates evaluation and code generation to the ocntained expression
 */
public class NodeFactExpr extends NodeFact {

	private NodeExpr expr;

	/**
	 * Constructs a NodeFactExpr with given expression
	 * @param expr
	 */
	public NodeFactExpr(NodeExpr expr) {
		this.expr=expr;
	}

	/**
	 * Evals enclosed expression
	 * @param env current environment containing variable value
	 * @return result of evaluating the inner expression
	 * @throws EvalException if an error occurs
	 */
	public int eval(Environment env) throws EvalException {
		return expr.eval(env);
	}

	/**
	 * Generates C code for parenthesized expression
	 * @return
	 */
	public String code() { return "("+expr.code()+")"; }

}
