/**
 * Represents a write (output) node in the parse tree.
 * This node is used
 * by the assignment node to handle printing of evaluated results.
 * It evaluates an expression and prints its value to standard output.
 */
public class NodeWr extends Node {

	private NodeExpr expr;

	/**
	 * Constructs a NodeWr with the given expression.
	 * @param expr the expression to evaluate and print
	 */
	public NodeWr(NodeExpr expr) {
		this.expr=expr;
	}

	/**
	 * Evaluates the expression and prints its value.
	 * If the result is an integer, it prints as an integer.
	 * Otherwise, it prints the full numeric value.
	 * @param env
	 * @return
	 * @throws EvalException
	 */
	public double eval(Environment env) throws EvalException {
		double d=expr.eval(env);
		int i=(int) d;
		if (i==d)
			System.out.println(i);
		else
			System.out.println(d);
		return d;
	}

	/**
	 * Generates equivalent C code to print the expression’s value.
	 * @return string of C code using printf
	 */
	public String code() {
		return "printf(\"%g\\n\","
			+"(double)("
			+expr.code()
			+"));";
	}

}
