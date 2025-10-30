/**
 * The exceoteption that occurs during expression eval
 *
 * thrown when it encounters a runtime error.
 */
public class EvalException extends Exception {
	/* position in the source program*/
	private int pos;
	/* message explaining the cause of error*/
	private String msg;

	/**
	 * makes the EvalException with a message and position
	 * @param pos position where erorr occured
	 * @param msg short description of error
	 */
	public EvalException(int pos, String msg) {
		this.pos=pos;
		this.msg=msg;
	}

	/**
	 * Formatted string describing this evaluation error.
	 * @return string containing error type, position and message
	 */
	public String toString() {
		return "eval error"
			+", pos="+pos
			+", "+msg;
	}

}
