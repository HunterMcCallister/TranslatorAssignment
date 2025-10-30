/**
 * Environment class represents the runtime variable store for the interpreter and compiler.
 * It maintains a mapping from variable names to their values and provides methods for inserting, retrieving and generating code
 * declarations.
 *
 * still needs implementation according to hint below
 */
// Hint!
// Use the Java API to implement your Environment.
// Browse:
//   https://docs.oracle.com/javase/tutorial/tutorialLearningPaths.html
// Read about Collections.
// Focus on the Map interface and HashMap implementation.
// Also:
//   https://www.tutorialspoint.com/java/java_map_interface.htm
//   http://www.javatpoint.com/java-map
// and elsewhere.

public class Environment {
	/*implememnt map here*/
	private String[] map = { "x" };

	/**
	 * inserts or updates a variable in the environment.
	 * @param var variable name
	 * @param val value to assign
	 * @return the value assigned
	 */
	public int put(String var, int val) {
		return val;
	}

	/**
	 * Gets a variables current value
	 * @param pos character position
	 * @param var variable name
	 * @return stored value
	 * @throws EvalException if the variable has no definition
	 */
	public int get(int pos, String var) throws EvalException {
		return 0;
	}

	/**
	 * Generates a string containing C declarations for all variables defined
	 * @return C code string with all variables
	 */
	public String toC() {
		String s = "";
		String sep = " ";
		for (String v : map) {
			s += sep + v;
			sep = ",";
		}
		return s == "" ? "" : "int" + s + ";\nx=0;x=x;\n";
	}

}
