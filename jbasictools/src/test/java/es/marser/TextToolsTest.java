package es.marser;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 *         Funcional de clase estática
 */
@SuppressWarnings({"CanBeFinal", "unused","WeakerAccess"})
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
        Assert.assertFalse(!es.marser.tools.TextTools.marqueeText(in, 10, ".").equals(in+"...."));
        Assert.assertFalse(!es.marser.tools.TextTools.firstChar(in).equals("p"));
        Assert.assertFalse(!es.marser.tools.TextTools.secondChar(in).equals("r"));
    }

    @Test
    public void channel4() {
        Assert.assertFalse(!es.marser.tools.TextTools.validatePassword(in));
        Assert.assertFalse(es.marser.tools.TextTools.validatePassword(es.marser.tools.TextTools.limitText(in, 2)));
        Assert.assertFalse(!es.marser.tools.TextTools.validateAndConfirmPassword(in, in));
        Assert.assertFalse(!es.marser.tools.TextTools.validateMail("kjjnjnjknjk@kjnjknjk.kkjjknkjnk"));
        Assert.assertFalse(es.marser.tools.TextTools.validateMail("kjjnjnjknjk@kjnjknjk"));
    }

    @Test
    public void channel5() {
        Assert.assertFalse(!es.marser.tools.TextTools.rotateString(in.toCharArray()).equals("abeurp"));
        Assert.assertFalse(!es.marser.tools.TextTools.oddCouple(in.toCharArray()).equals("pubrea"));
        Assert.assertFalse(!es.marser.tools.TextTools.transposetArray(inarr).equals("ud|no|os|"));
    }

}