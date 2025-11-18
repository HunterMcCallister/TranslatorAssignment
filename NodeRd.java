public class NodeRd extends Node {

    private String id;

    public NodeRd(String id) {
        this.id = id;
    }

    public double eval(Environment env) throws EvalException {
        java.util.Scanner input = new java.util.Scanner(System.in);
        System.out.print(id + " = ");
        double val = input.nextDouble();
        env.put(id, val);
        return val;
    }

    public String code() {
        return "scanf(\"%lf\", &" + id + ");";
    }
}
