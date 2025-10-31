/**
 * Represents an expression node in the parse tree
 * Each NodeExpr stores one term an opptional addop and an optional following expression
 * Can be evaluated or translated into C code
 */
public class NodeExpr extends Node {

	private NodeTerm term;
	private NodeAddop addop;
	private NodeExpr expr;

	/**
	 * Constructs an expression node made of a term, optional addop and expr
	 * @param term lefthand term
	 * @param addop addition or subtration or null
	 * @param expr the expression or null
	 */
	public NodeExpr(NodeTerm term, NodeAddop addop, NodeExpr expr) {
		this.term=term;
		this.addop=addop;
		this.expr=expr;
	}

	/**
	 * Appends another expresion node to this one.
	 * Used to add recursive parts of the expression tree together.
	 * @param expr another NodeExpr
	 */
	public void append(NodeExpr expr) {
		if (this.expr==null) {
			this.addop=expr.addop;
			this.expr=expr;
			expr.addop=null;
		} else
			this.expr.append(expr);
	}

	/**
	 * Evaluates this expression recursively.
	 * If there is no addop the value of the term is returned.
	 * otherwise the operator is applied to the evaluated sub expressions.
	 * @param env current environment with the variables
	 * @return the result of evaluating the expression
	 * @throws EvalException if an error occurs during evaluation
	 */
	public double eval(Environment env) throws EvalException {
		return expr==null
			? term.eval(env)
			: addop.op(expr.eval(env),term.eval(env));
	}

	/**
	 * Makes the equivalent C code for this expression.
	 * @return C code
	 */
	public String code() {
		return (expr==null ? "" : expr.code()+addop.code())+term.code();
	}

}
