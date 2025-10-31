/**
 * Represents a multiplication or division operator node in the parse tree.
 *
 * A NodeMulop stores the operator symbol and provides methods for
 * performing arithmetic during evaluation and generating equivalent code.
 */
public class NodeMulop extends Node {

	private String mulop;

	/**
	 * Constructs a NodeMulop with the given source position and operator.
	 * @param pos the character position in the source code
	 * @param mulop the operator symbol ('*' or '/')
	 */
	public NodeMulop(int pos, String mulop) {
		this.pos=pos;
		this.mulop=mulop;
	}

	/**
	 * Applies this multiplication or division operator to two operand values.
	 * @param o1 left hand opp
	 * @param o2 right hand opp
	 * @return result of applying opp
	 * @throws EvalException if opp invalid or div by zero
	 */
	public double op(double o1, double o2) throws EvalException {
		if (mulop.equals("*"))
			return o1*o2;
		if (mulop.equals("/"))
			return o1/o2;
		throw new EvalException(pos,"bogus mulop: "+mulop);
	}

	/**
	 *  Returns the operator symbol as C source code.
	 * @return the '*' or '/' character as a string
	 */
	public String code() { return mulop; }

}
