/**
 * Main Class that is the entry point to the interpreter.
 * each command line arg represents the complete program written. Program is scanned, parsed into a parse tree and then evaluated
 * all evals share the same isntance.
 */

public class Main {
	/**
	 * entry to the translator
	 * @param args langouage
	 */
	public static void main(String[] args) {
		Parser parser=new Parser();
		Environment env=new Environment();
		String code="";
		for (String prog: args)
			try {
				Node node=parser.parse(prog);
				node.eval(env);
				code+=node.code();
			} catch (Exception e) {
				System.err.println(e);
			}
		new Code(code,env);
	}

}
