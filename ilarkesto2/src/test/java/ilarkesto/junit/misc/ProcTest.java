package ilarkesto.junit.misc;

import ilarkesto.base.Proc;
import static java.lang.System.out;
import junit.framework.TestCase;

/**
 *
 *
 */
public class ProcTest extends TestCase {

    /**
     *
     */
    public void test() {
		Proc proc = new Proc("java");
		proc.addParameter("-version");
		proc.start();
		proc.waitFor();
		out.println(proc.getReturnCode());
		out.println(proc.getOutput());
	}

}
