/**
 * Represent an assignment statement node in the parse tree.
 *
 * A NodeAssn stores a variable identifier and the expression whose evaluated result is assigned to that variable
 */
public class NodeAssn extends Node {

	private String id;
	private NodeExpr expr;

	/**
	 * Constructs a NodeAssn with a variable identifier and an expression
	 * @param id var name
	 * @param expr expression with the value to assign
	 */
	public NodeAssn(String id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	/**
	 * evaluates the assignment by evaluating the expression adn storing the result in the environment
	 * @param env environment with variable values
	 * @return assigned value
	 * @throws EvalException if an evaluation error occurs
	 */
	public double eval(Environment env) throws EvalException {
		return env.put(id, new NodeWr(expr).eval(env));
	}

	/**
	 * Makes the C code for the assignment statement
	 * @return C source code
	 */
	public String code() {
		return id + "=" + expr.code() + ";\n" + new NodeWr(expr).code();
	}

}
