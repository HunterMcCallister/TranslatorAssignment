/**
 * Represents a negated factor, such as -x or -(x+3).
 */
public class NodeFactNeg extends NodeFact {

    private NodeFact fact;

    public NodeFactNeg(NodeFact fact) {
        this.fact = fact;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        return -fact.eval(env);
    }

    @Override
    public String code() {
        return "-" + fact.code();
    }
}
