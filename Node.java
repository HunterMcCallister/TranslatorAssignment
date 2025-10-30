/**
 * Abstract node class that represents a single node in the parse tree
 */

public abstract class Node {
	/* position of the node in the input source */
	protected int pos=0;

	/**
	 * Evaluates the node within the environment.
	 * @param env current run time environment
	 * @return numeric result of evaluating
	 * @throws EvalException if node cannot be evaluated
	 */
	public int eval(Environment env) throws EvalException {
		throw new EvalException(pos,"cannot eval() node!");
	}

	/**
	 * generates the C source code for this node
	 * @return string of C source code
	 */
	public String code() { return ""; }

}
