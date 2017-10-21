package es.marser.crypt;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CreateCode {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void create() {
        /*
        int[] in = {2,12,17,23,27,32,35,43,47,55,61,69,74,78,87,92,103,107,116,127,134,142,147,157,166,177,186,193,201,208,216,224,228,234,239,246,257,264,267,270,275,285,291};
        for (int i = 0; i < in.length - 1; ++i) {
            System.out.println(concatetar(in[i], in[i + 1]));
        }
        */
        System.out.println(concatetar(2, 45));
    }

    public String concatetar(int start, int end) {

        String split = "$E$1";
        StringBuilder builder = new StringBuilder();
        builder.append("");
        System.out.print("=CONCATENAR(");
        for (int i = ++start; i < end; ++i) {
            builder.append("E").append(i).append(";").append(split).append(";");
        }

        return builder.toString().substring(0, builder.length() - 1) + ")";
    }
}