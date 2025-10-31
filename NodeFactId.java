/**
 * Represents a variable reference node in the parse tree.
 *
 * nodeFactId stores the variable name and retrieves its value from the environment when evaled
 */
public class NodeFactId extends NodeFact {

	private String id;

	/**
	 * Constructs a NodeFactId wsith the given source position and variable name
	 * @param pos character position in the source code
	 * @param id variable identifier
	 */
	public NodeFactId(int pos, String id) {
		this.pos=pos;
		this.id=id;
	}

	/**
	 * evaluates this factor by looking up the variables value in the environment
	 * @param env current envrionment containing variable bindings
	 * @return value of the variable
	 * @throws EvalException if variable is undefined
	 */
	public double eval(Environment env) throws EvalException {
		return env.get(pos,id);
	}

	/**
	 * Generates C code for this variable
	 * @return variable name as a string
	 */
	public String code() { return id; }

}
