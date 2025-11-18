/**
 * Represents a statement node in the parse tree.
 *
 * A NodeStmt wraps an assignment statement and delegates
 * evaluation and code generation to its contained NodeAssn.
 */
public class NodeStmt extends Node {

	private NodeAssn assn;
	private NodeRd rd;
	private NodeWr wr;
	private NodeWhile whileNode;
	private NodeIf ifNode;

	/**
	 * Constructs a NodeStmt with the given assignment node.
	 * 
	 * @param assn the assignment node representing the statement
	 */
	public NodeStmt(NodeAssn assn) {
		this.assn = assn;
	}

	public NodeStmt(NodeRd rd) {
		this.rd = rd;
	}

	public NodeStmt(NodeWr wr) {
		this.wr = wr;
	}

	public NodeStmt(NodeWhile whileNode) {
		this.whileNode = whileNode;
	}

	public NodeStmt(NodeIf ifNode) {
		this.ifNode = ifNode;
	}

	/**
	 * Evaluates this statement by evaluating its contained assignment.
	 * 
	 * @param env the current environment containing variable bindings
	 * @return the value produced by the assignment
	 * @throws EvalException if an error occurs
	 */
	public double eval(Environment env) throws EvalException {
		if (assn != null)
			return assn.eval(env);
		if (rd != null)
			return rd.eval(env);
		if (wr != null)
			return wr.eval(env);
		throw new EvalException(0, "invalid statement");
	}

	/**
	 * Generates equivalent C code for this statement.
	 * 
	 * @return string of C code representing the statement
	 */
	public String code() {
		if (assn != null)
			return assn.code();
		if (rd != null)
			return rd.code();
		if (wr != null)
			return wr.code();
		return "";
	}

}
