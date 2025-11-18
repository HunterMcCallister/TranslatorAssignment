/**
 * Parser implements recursion for the source language. each method corresponds
 * to a production in the grammar file.
 * It uses a scanner to read the tokens abd build a parse tree of nodes.
 */

public class Parser {
	/** the scanner that provides the tokens for the parser */
	private Scanner scanner;

	/**
	 * Ensures the token matches the expected symbol.
	 * Goes onto the next if it is successful.
	 * 
	 * @param s token String
	 * @throws SyntaxException if token does not match
	 */
	private void match(String s) throws SyntaxException {
		scanner.match(new Token(s));
	}

	/**
	 * Returns the current token without advancing.
	 * 
	 * @return current token
	 * @throws SyntaxException if scanner finds invalid syntax
	 */
	private Token curr() throws SyntaxException {
		return scanner.curr();
	}

	/**
	 * @return the current position
	 */
	private int pos() {
		return (int) scanner.pos();
	}

	/**
	 * Parses a multiplication or division
	 *
	 * @return NodeMulop if found or null
	 * @throws SyntaxException if parsing fails
	 */
	private NodeMulop parseMulop() throws SyntaxException {
		if (curr().equals(new Token("*"))) {
			match("*");
			return new NodeMulop((int) pos(), "*");
		}
		if (curr().equals(new Token("/"))) {
			match("/");
			return new NodeMulop((int) pos(), "/");
		}
		return null;
	}

	/**
	 * parses addition or subtraction
	 * 
	 * @return NodeAddop if found or null
	 * @throws SyntaxException if it fails
	 */
	private NodeAddop parseAddop() throws SyntaxException {
		if (curr().equals(new Token("+"))) {
			match("+");
			return new NodeAddop((int) pos(), "+");
		}
		if (curr().equals(new Token("-"))) {
			match("-");
			return new NodeAddop((int) pos(), "-");
		}
		return null;
	}

	/**
	 * Parses a factor (number,variable or expression)
	 * 
	 * @return NodeFact for a parsed factor
	 * @throws SyntaxException if it fails
	 */
	private NodeFact parseFact() throws SyntaxException {
		if (curr().equals(new Token("-"))) {
			match("-");
			NodeFact fact = parseFact();
			return new NodeFactNeg(fact);
		}

		if (curr().equals(new Token("("))) {
			match("(");
			NodeExpr expr = parseExpr();
			match(")");
			return new NodeFactExpr(expr);
		}
		if (curr().equals(new Token("id"))) {
			Token id = curr();
			match("id");
			return new NodeFactId((int) pos(), id.lex());
		}
		Token num = curr();
		match("num");
		return new NodeFactNum(num.lex());
	}

	/**
	 * Parses a term (a factor followed by a * or / and another term)
	 * 
	 * @return a NodeTerm representing a term
	 * @throws SyntaxException if it fails
	 */
	private NodeTerm parseTerm() throws SyntaxException {
		NodeFact fact = parseFact();
		NodeMulop mulop = parseMulop();
		if (mulop == null)
			return new NodeTerm(fact, null, null);
		NodeTerm term = parseTerm();
		term.append(new NodeTerm(fact, mulop, null));
		return term;
	}

	/**
	 * Parses the expression (term followed by + or - and another expression
	 * 
	 * @return NodeExpr for this expression
	 * @throws SyntaxException if it fails...
	 */
	private NodeExpr parseExpr() throws SyntaxException {
		NodeTerm term = parseTerm();
		NodeAddop addop = parseAddop();
		if (addop == null)
			return new NodeExpr(term, null, null);
		NodeExpr expr = parseExpr();
		expr.append(new NodeExpr(term, addop, null));
		return expr;
	}

	/**
	 * parse the assignment statement
	 * 
	 * @return NodeAssn for the assignment
	 * @throws SyntaxException if. it. fails.
	 */
	private NodeAssn parseAssn() throws SyntaxException {
		Token id = curr();
		match("id");
		match("=");
		NodeExpr expr = parseExpr();
		NodeAssn assn = new NodeAssn(id.lex(), expr);
		return assn;
	}

	/**
	 * parses a block of statements
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private NodeProg parseProg() throws SyntaxException {
		NodeBlock block = parseBlock();
		return new NodeProg(block);
	}

	/**
	 * parses a block of statements
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private NodeBlock parseBlock() throws SyntaxException {
		NodeStmt stmt = parseStmt();
		if (curr().equals(new Token(";"))) {
			match(";");
			NodeBlock block = parseBlock();
			return new NodeBlock(stmt, block);
		}
		return new NodeBlock(stmt, null);
	}

	private NodeBoolExpr parseBoolExpr() throws SyntaxException {
		NodeExpr expr1 = parseExpr();
		Token relop = curr();
		match(relop.lex());
		NodeExpr expr2 = parseExpr();
		return new NodeBoolExpr(expr1, relop.lex(), expr2);
	}

	/**
	 * Parse the complete statement (assignment followed by semicolon)
	 * 
	 * @return NodeStmt object representing the statement
	 * @throws SyntaxException if it fails
	 */
	private NodeStmt parseStmt() throws SyntaxException {
		if (curr().equals(new Token("id"))) {
			NodeAssn assn = parseAssn();
			match(";");
			return new NodeStmt(assn);
		}

		if (curr().equals(new Token("rd"))) {
			match("rd");
			Token id = curr();
			match("id");
			match(";");
			return new NodeStmt(new NodeRd(id.lex()));
		}

		if (curr().equals(new Token("wr"))) {
			match("wr");
			NodeExpr expr = parseExpr();
			match(";");
			return new NodeStmt(new NodeWr(expr));
		}

		if (curr().equals(new Token("if"))) {
			match("if");
			NodeBoolExpr boolExpr = parseBoolExpr();
			match("then");
			NodeStmt thenStmt = parseStmt();

			NodeStmt elseStmt = null;
			if (curr().equals(new Token("else"))) {
				match("else");
				elseStmt = parseStmt();
			}
			return new NodeStmt(new NodeIf(boolExpr, thenStmt, elseStmt));
		}

		if (curr().equals(new Token("while"))) {
			match("while");
			NodeBoolExpr boolExpr = parseBoolExpr();
			match("do");
			NodeStmt stmt = parseStmt();
			return new NodeStmt(new NodeWhile(boolExpr, stmt));
		}

		throw new SyntaxException(pos(), new Token("stmt"), curr());
	}

	/**
	 * parses the entire string
	 * 
	 * @param program input code
	 * @return the root node of the syntax tree
	 * @throws SyntaxException if parsing fails
	 */
	public Node parse(String program) throws SyntaxException {
		scanner = new Scanner(program);
		scanner.next();
		NodeProg prog = parseProg();
		match("EOF");
		return prog;
	}

}
