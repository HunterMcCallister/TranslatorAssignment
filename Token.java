// This class models a token, which has two parts:
// 1) the token itself (e.g., "id" or "+")
// 2) the token's lexeme (e.g., "foo")
/**
 * Represents a token produced by the scanner.
 *
 * A token consists of two parts:
 * 1) The token type (e.g., "id", "num", "+")
 * 2) The token’s lexeme, which is the exact string from the source (e.g., "foo", "42", "+")
 */
public class Token {
	/** The token type (e.g., "id", "num", "+"). */
	private String token;
	/** The specific text (lexeme) associated with this token. */
	private String lexeme;

	/**
	 * Constructs a Token where the type and lexeme are the same.
	 * @param token  type
	 * @param lexeme type
	 */
	public Token(String token, String lexeme) {
		this.token=token;
		this.lexeme=lexeme;
	}
	/**
	 * Returns the token type.
	 *
	 * @return the token type string
	 */
	public Token(String token) {
		this(token,token);
	}

	/**
	 * Returns the token type.
	 *
	 * @return the token type string
	 */

	public String tok() { return token; }
	/**
	 * Returns the token’s lexeme.
	 *
	 * @return the lexeme string
	 */

	public String lex() { return lexeme; }

	/**
	 * Checks if this token has the same type as another token.
	 *
	 * @param t the token to compare with
	 * @return true if both tokens have the same type
	 */
	public boolean equals(Token t) {
		return token.equals(t.token);
	}

	/**
	 * Returns a string representation of this token.
	 *
	 * @return the formatted string "<token,lexeme>"
	 */
	public String toString() {
		return "<"+tok()+","+lex()+">";
	}

}
