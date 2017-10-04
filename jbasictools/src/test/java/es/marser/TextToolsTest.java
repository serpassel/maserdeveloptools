package es.marser;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 *         Funcional de clase estática
 */
@SuppressWarnings("CanBeFinal")
public class TextToolsTest {

    private String in = "prueba";
    private String[] inarr = {"uno", "dos"};

    @Test
    public void channel1() {
        Assert.assertFalse(!TextTools.isHexadecimal("2E6A3"));
        Assert.assertFalse(!TextTools.isHexadecimal("2E6.A3"));
        Assert.assertFalse(TextTools.isHexadecimal("2E6&&&&"));
        Assert.assertFalse(!TextTools.isNumeric("1.5"));
        Assert.assertFalse(!TextTools.isNumeric("1"));
        Assert.assertFalse(!TextTools.isNumeric("-1.5"));
        Assert.assertFalse(TextTools.isNumeric(in));
        Assert.assertFalse(TextTools.isNumeric(in));
    }

    @Test
    public void channel3() {
        Assert.assertFalse(!TextTools.capitalizeFirstChar(in).equals("Prueba"));
        Assert.assertFalse(!TextTools.limitText(in, 3).equals("pru"));
        Assert.assertFalse(!TextTools.lastText(in, 3).equals("eba"));
        Assert.assertFalse(!TextTools.limitMarqueeText(in, 2, "...").equals("pr..."));
        Assert.assertFalse(!TextTools.limitMarqueeText(in, 10, "...").equals(in));
        Assert.assertFalse(!TextTools.marqueeText(in, 10, ".").equals(in+"...."));
        Assert.assertFalse(!TextTools.firstChar(in).equals("p"));
        Assert.assertFalse(!TextTools.secondChar(in).equals("r"));
    }

    @Test
    public void channel4() {
        Assert.assertFalse(!TextTools.validatePassword(in));
        Assert.assertFalse(TextTools.validatePassword(TextTools.limitText(in, 2)));
        Assert.assertFalse(!TextTools.validateAndConfirmPassword(in, in));
        Assert.assertFalse(!TextTools.validateMail("kjjnjnjknjk@kjnjknjk.kkjjknkjnk"));
        Assert.assertFalse(TextTools.validateMail("kjjnjnjknjk@kjnjknjk"));
    }

    @Test
    public void channel5() {
        Assert.assertFalse(!TextTools.rotateString(in.toCharArray()).equals("abeurp"));
        Assert.assertFalse(!TextTools.oddCouple(in.toCharArray()).equals("pubrea"));
        Assert.assertFalse(!TextTools.transposetArray(inarr).equals("ud|no|os|"));
    }

}