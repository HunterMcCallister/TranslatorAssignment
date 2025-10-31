/**
 * Represent a numeric literal node in the parse tree
 *
 * A NodeFactNum stores a number as a string and converts it to an integer during evaluation
 */
public class NodeFactNum extends NodeFact {

	private String num;

	/**
	 * Constructs a NodeFactNum with the given numeric literal
	 * @param num the number in string form
	 */
	public NodeFactNum(String num) {
		this.num=num;
	}

	/**
	 * Evaluates this factor by converting the stored string to an integer value
	 * @param env current environmnet (unused for literals)
	 * @return the numeric value of this literal
	 * @throws EvalException if parsing the number fails
	 */
	public double eval(Environment env) throws EvalException {
		return Double.parseDouble(num);
	}

	/**
	 * Generates C code for num literal
	 * @return number as a string
	 */
	public String code() { return num; }

}
