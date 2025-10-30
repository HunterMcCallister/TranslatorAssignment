/**
 * The Scanner class performs lexical analysis on the input program.
 *
 * It reads the raw source code, breaks it into tokens (numbers, identifiers,
 * operators, etc...) and provides them one at a time to the parser.
 *
 * Each token corresponds to a lexeme defined by the programming language grammar.
 */
import java.util.*;

public class Scanner {

	private String program;		// source program being interpreted
	private int pos;			// index of next char in program
	private Token token;		// last/current scanned token

	// sets of various characters and lexemes
	private Set<String> whitespace=new HashSet<String>();
	private Set<String> digits=new HashSet<String>();
	private Set<String> letters=new HashSet<String>();
	private Set<String> legits=new HashSet<String>();
	private Set<String> keywords=new HashSet<String>();
	private Set<String> operators=new HashSet<String>();

	/**
	 * Fills a set with all characters from low to hi inclusive
	 * @param s the set to fill
	 * @param lo the first character in the range
	 * @param hi the last character in the range
	 */
	private void fill(Set<String> s, char lo, char hi) {
		for (char c=lo; c<=hi; c++)
			s.add(c+"");
	}

	/**
	 * Initializes the whitespace character set
	 */
	private void initWhitespace(Set<String> s) {
		s.add(" ");
		s.add("\n");
		s.add("\t");
	}


	/** Initializes the digit character set (0–9). */
	private void initDigits(Set<String> s) {
		fill(s,'0','9');
	}
	/** Initializes the letter character set (A–Z, a–z). */
	private void initLetters(Set<String> s) {
		fill(s,'A','Z');
		fill(s,'a','z');
	}
	/** Initializes the legal alphanumeric character set (letters + digits). */
	private void initLegits(Set<String> s) {
		s.addAll(letters);
		s.addAll(digits);
	}
	/** Initializes the operator character set. */
	private void initOperators(Set<String> s) {
		s.add("=");
		s.add("+");
		s.add("-");
		s.add("*");
		s.add("/");
		s.add("(");
		s.add(")");
		s.add(";");
	}
	/** Initializes the keyword set. (Empty for now.) */
	private void initKeywords(Set<String> s) {
	}

	/**
	 * Constructs a scanner for the given source program
	 * @param program source code to be scanned
	 */
	// constructor:
	//     - squirrel-away source program
	//     - initialize sets
	public Scanner(String program) {
		this.program=program;
		pos=0;
		token=null;
		initWhitespace(whitespace);
		initDigits(digits);
		initLetters(letters);
		initLegits(legits);
		initKeywords(keywords);
		initOperators(operators);
	}

	// handy string-processing methods
	/** @return true if the end of the program has been reached. */
	public boolean done() {
		return pos>=program.length();
	}

	/**
	 * Advances the position while the current character belongs to the given set.
	 * @param s set of allowed characters
	 */
	private void many(Set<String> s) {
		while (!done()&&s.contains(program.charAt(pos)+""))
			pos++;
	}

	// This method advances the scanner,
	// until the current input character
	// is just after a sequence of one or more
	// of a particular character.
	// Arguments:
	//     c = the character to search for
	// Members:
	//     program = the scanner's input
	//     pos = index of current input character

	/**
	 * Advances the position until after a specified character appears.
	 * @param c character to skip past
	 */
	private void past(char c) {
		while (!done()&&c!=program.charAt(pos))
			pos++;
		if (!done()&&c==program.charAt(pos))
			pos++;
	}

	// scan various kinds of lexeme
	/** Scans a numeric literal token. */
	private void nextNumber() {
		int old=pos;
		many(digits);
		token=new Token("num",program.substring(old,pos));
	}
	/** Scans an identifier or keyword token. */
	private void nextKwId() {
		int old=pos;
		many(letters);
		many(legits);
		String lexeme=program.substring(old,pos);
		token=new Token((keywords.contains(lexeme) ? lexeme : "id"),lexeme);
	}
	/** Scans an operator token (one or two characters). */
	private void nextOp() {
		int old=pos;
		pos=old+2;
		if (!done()) {
			String lexeme=program.substring(old,pos);
			if (operators.contains(lexeme)) {
				token=new Token(lexeme); // two-char operator
				return;
			}
		}
		pos=old+1;
		String lexeme=program.substring(old,pos);
		token=new Token(lexeme); // one-char operator
	}

	// This method determines the kind of the next token (e.g., "id"),
	// and calls a method to scan that token's lexeme (e.g., "foo").

	/**
	 * advances to the next token in the input
	 * @return false if EOF reached, true otherwise
	 */
	public boolean next() {

		if (!done()) {
			if(program.startsWith("//", pos)){ //skip comments with '//'
				while(!done() && program.charAt(pos) != '\n')
					pos++;
				return next();
			}
			if (program.charAt(pos) == '#'){ //skip comments with '#'
				while (!done() && program.charAt(pos) != '\n')
					pos++;
				return next();
			}
		}

		many(whitespace);
		if (done()) {
			token=new Token("EOF");
			return false;
		}


		String c=program.charAt(pos)+"";
		if (digits.contains(c))
			nextNumber();
		else if (letters.contains(c))
			nextKwId();
		else if (operators.contains(c))
			nextOp();
		else {
			System.err.println("illegal character at position "+pos);
			pos++;
			return next();
		}
		return true;
	}

	// This method scans the next lexeme,
	// if the current token is the expected token.

	/**
	 * Matches teh current token against an expected one and then advances
	 * @param t expected token
	 * @throws SyntaxException if current token does not match
	 */
	public void match(Token t) throws SyntaxException {
		if (!t.equals(curr()))
			throw new SyntaxException(pos,t,curr());
		next();
	}

	/**
	 * Returns the current token without advancing
	 * @return curr token
	 * @throws SyntaxException if called before scanning any tokens
	 */
	public Token curr() throws SyntaxException {
		if (token==null)
			throw new SyntaxException(pos,new Token("ANY"),new Token("EMPTY"));
		return token;
	}
	/** @return the current position in the source code. */
	public int pos() {
		return pos;
	}

	// for unit testing

	/** Simple test driver that prints all tokens in the given input. */
	public static void main(String[] args) {
		try {
			Scanner scanner=new Scanner(args[0]);
			while (scanner.next())
				System.out.println(scanner.curr());
		} catch (SyntaxException e) {
			System.err.println(e);
		}
	}

}
