/**
 * Addition and subtraction operator node in the parse tree.
 * NodeAddop stores the operator and provides methods for performing arithmetic during eval and generation of code output
 */
public class NodeAddop extends Node {

	/* Operator symbol (+ or -)*/
	private String addop;

	/**
	 * Constructs a NodeAddop with the given source position and operator
	 * @param pos character position in the source code
	 * @param addop (+ or -)
	 */
	public NodeAddop(int pos, String addop) {
		this.pos=pos;
		this.addop=addop;
	}

	/**
	 * Applies addition or subtraction to two operand values
	 * @param o1 left hand side
	 * @param o2 right hand side
	 * @return result of applying
	 * @throws EvalException if operator symbol is invalid
	 */
	public double op(double o1, double o2) throws EvalException {
		if (addop.equals("+"))
			return o1+o2;
		if (addop.equals("-"))
			return o1-o2;
		throw new EvalException(pos,"bogus addop: "+addop);
	}

	/**
	 * returns op symbol as C code
	 * @return string
	 */
	public String code() { return addop; }

}
