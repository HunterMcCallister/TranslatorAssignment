/**
 * Represents a syntax error detected by the parser.
 *
 * A SyntaxException is thrown when the current token does not match
 * the expected one according to the grammar rules.
 */
public class SyntaxException extends Exception {

	/** The position (character index) in the source code where the error occurred. */
	private int pos;

	/** The token that the parser expected to see. */
	private Token expected;

	/** The token that the parser actually found. */
	private Token found;

	/**
	 * Constructs a SyntaxException with position, expected token, and found token.
	 *
	 * @param pos      the position in the input where the error occurred
	 * @param expected the token that was expected
	 * @param found    the token that was actually found
	 */
	public SyntaxException(int pos, Token expected, Token found) {
		this.pos = pos;
		this.expected = expected;
		this.found = found;
	}

	/**
	 * Returns a formatted string describing the syntax error.
	 *
	 * @return a string containing position, expected token, and found token
	 */
	public String toString() {
		return "syntax error"
				+ ", pos=" + pos
				+ ", expected=" + expected
				+ ", found=" + found;
	}
}
