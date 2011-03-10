package cz.vutbr.fit.convert;

/**
 *
 * @author pavel
 */
public class Log {
    
    public static void debug(String msg) {
        if(Convert.DEBUG) {
            System.out.println(msg);
        }
    }
}
