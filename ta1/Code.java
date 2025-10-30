import java.io.*;

/**
 * Code class handles the compiler output portion of the translator
 *
 * Writes generated C source code to a file with name provided by the environment variable Code.
 * Output includes standard prologue and epilogue
 */
public class Code {
	/**
	 * lines that appear at the top of C file
	 */
	private final String[] prologue={
		"#include <stdio.h>",
		"int main() {",
	};
	/**
	 * lines that appear at the bottome of a C file.
	 */
	private final String[] epilogue={
		"return 0;",
		"}",
	};

	/**
	 * Constructs the Code object that writes out the translated C code.
	 * @param code body of generated C code from the parse tree
	 * @param env the environment object containing variables
	 */
	public Code(String code, Environment env) {
		String fn=System.getenv("Code");
		if (fn==null)
			return;
		try {
			BufferedWriter f=new BufferedWriter(new FileWriter(fn+".c"));
			for (String s: prologue)
				f.write(s+"\n");
			f.write(env.toC());
			f.write(code);
			for (String s: epilogue)
				f.write(s+"\n");
			f.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
