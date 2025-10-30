/**
 * Represents a term node in the parse tree.
 *
 * A NodeTerm stores one factor, an optional multiplication or division operator,
 * and an optional following term. It supports both evaluation and code generation.
 */
public class NodeTerm extends Node {

	private NodeFact fact;
	private NodeMulop mulop;
	private NodeTerm term;

	/**
	 * Constructs a NodeTerm with a factor, an optional operator, and an optional term.
	 *
	 * @param fact factor of this term
	 * @param mulop Multiplication or division opp (can be null)
	 * @param term the next term in the sequence (can be null)
	 */
	public NodeTerm(NodeFact fact, NodeMulop mulop, NodeTerm term) {
		this.fact=fact;
		this.mulop=mulop;
		this.term=term;
	}

	/**
	 * Appends another term to this one, forming a recursive chain of terms.
	 * @param term another NodeTerm to append
	 */
	public void append(NodeTerm term) {
		if (this.term==null) {
			this.mulop=term.mulop;
			this.term=term;
			term.mulop=null;
		} else
			this.term.append(term);
	}

	/**
	 * Evaluates this term recursively.
	 * If there is no operator, returns the value of the factor.
	 * Otherwise, applies the operator to the subterms.
	 * @param env the current environment containing variable values
	 * @return the numeric result of evaluating the term
	 * @throws EvalException if an error occurs during evaluation
	 */
	public int eval(Environment env) throws EvalException {
		return term==null
			? fact.eval(env)
			: mulop.op(term.eval(env),fact.eval(env));
	}

	/**
	 * Generates equivalent C code for this term.
	 * @return string of C source code
	 */
	public String code() {
		return (term==null ? "" : term.code()+mulop.code())+fact.code();
	}

}
