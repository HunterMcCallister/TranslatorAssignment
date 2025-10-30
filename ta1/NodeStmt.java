/**
 * Represents a statement node in the parse tree.
 *
 * A NodeStmt wraps an assignment statement and delegates
 * evaluation and code generation to its contained NodeAssn.
 */
public class NodeStmt extends Node {

	private NodeAssn assn;

	/**
	 * Constructs a NodeStmt with the given assignment node.
	 * @param assn the assignment node representing the statement
	 */
	public NodeStmt(NodeAssn assn) {
		this.assn=assn;
	}

	/**
	 * Evaluates this statement by evaluating its contained assignment.
	 * @param env the current environment containing variable bindings
	 * @return the value produced by the assignment
	 * @throws EvalException if an error occurs
	 */
	public int eval(Environment env) throws EvalException {
		return assn.eval(env);
	}

	/**
	 * Generates equivalent C code for this statement.
	 * @return string of C code representing the statement
	 */
	public String code() { return assn.code(); }

}
