package es.marser.tools;

import org.junit.Assert;
import org.junit.Test;

import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 *         Funcional de clase estática
 */
@SuppressWarnings({"CanBeFinal", "unused", "WeakerAccess"})
public class TextToolsTest {

    private String in = "prueba";
    private String[] inarr = {"uno", "dos"};

    @Test
    public void channel1() {
        Assert.assertFalse(!es.marser.tools.TextTools.isHexadecimal("2E6A3"));
        Assert.assertFalse(!es.marser.tools.TextTools.isHexadecimal("2E6.A3"));
        Assert.assertFalse(es.marser.tools.TextTools.isHexadecimal("2E6&&&&"));
        Assert.assertFalse(!es.marser.tools.TextTools.isNumeric("1.5"));
        Assert.assertFalse(!es.marser.tools.TextTools.isNumeric("1"));
        Assert.assertFalse(!es.marser.tools.TextTools.isNumeric("-1.5"));
        Assert.assertFalse(es.marser.tools.TextTools.isNumeric(in));
        Assert.assertFalse(es.marser.tools.TextTools.isNumeric(in));
    }

    @Test
    public void channel3() {
        Assert.assertFalse(!es.marser.tools.TextTools.capitalizeFirstChar(in).equals("Prueba"));
        Assert.assertFalse(!es.marser.tools.TextTools.limitText(in, 3).equals("pru"));
        Assert.assertFalse(!es.marser.tools.TextTools.lastText(in, 3).equals("eba"));
        Assert.assertFalse(!es.marser.tools.TextTools.limitMarqueeText(in, 2, "...").equals("pr..."));
        Assert.assertFalse(!es.marser.tools.TextTools.limitMarqueeText(in, 10, "...").equals(in));
        Assert.assertFalse(!es.marser.tools.TextTools.marqueeText(in, 10, ".").equals(in + "...."));
        Assert.assertFalse(!es.marser.tools.TextTools.firstChar(in).equals("p"));
        Assert.assertFalse(!es.marser.tools.TextTools.secondChar(in).equals("r"));
        Assert.assertEquals(3, TextTools.charOccurrences("nhj|iijj|kjijih|", TextTools.OBJECT_SEPARATOR_CHAR));
        Assert.assertEquals(3, TextTools.charOccurrences("nhjRT&iijjRT&kjijihRT&", "RT&"));
    }

    @Test
    public void channel4() {
        Assert.assertFalse(!es.marser.tools.TextTools.validatePassword(in,6));
        Assert.assertFalse(es.marser.tools.TextTools.validatePassword(es.marser.tools.TextTools.limitText(in, 2),6));
        Assert.assertFalse(TextTools.validateAndConfirmPassword(in, in));
        Assert.assertFalse(!es.marser.tools.TextTools.validateMail("kjjnjnjknjk@kjnjknjk.kkjjknkjnk"));
        Assert.assertFalse(es.marser.tools.TextTools.validateMail("kjjnjnjknjk@kjnjknjk"));
        Assert.assertEquals(3, TextTools.charOccurrences("ttttt,tttt,ttttt,", TextTools.COMMA));
        Assert.assertEquals(3, TextTools.charOccurrences("ttttt.tttt.ttttt.", TextTools.POINT));
    }

    @Test
    public void channel5() {
        Assert.assertFalse(!es.marser.tools.TextTools.rotateString(in.toCharArray()).equals("abeurp"));
        Assert.assertFalse(!es.marser.tools.TextTools.oddCouple(in.toCharArray()).equals("pubrea"));
        Assert.assertFalse(!es.marser.tools.TextTools.transposetArray(inarr).equals("ud|no|os|"));
    }

    @Test
    public void channel6() {
        String brand = ", ";
        String text = "PASS";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            builder.append(text).append(brand);
        }
        Assert.assertEquals(TextTools.deleteLastBrand(builder, brand),"PASS, PASS, PASS, PASS");
    }
}