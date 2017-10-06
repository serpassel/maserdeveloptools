package es.marser;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


/**
 * @author sergio
 *         Created by sergio on 24/09/17.
 *         Test de herramientas mateáticas básicas
 *         [EN]  Test of basic mathematic tools
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class MathToolsTest {
    @Test
    public void channel2() {
        Assert.assertEquals(new BigDecimal("2.4"), es.marser.tools.MathTools.round(new BigDecimal("2.3647889"), 1));
        Assert.assertEquals(new BigDecimal("2.4"), es.marser.tools.MathTools.round(new BigDecimal("2.3547889"), 1));
        Assert.assertEquals(new BigDecimal("2.36"), es.marser.tools.MathTools.round(new BigDecimal("2.36"), -1));
        Assert.assertEquals(new BigDecimal("-2.4"), es.marser.tools.MathTools.round(new BigDecimal("-2.36"), 1));
        Assert.assertEquals(new BigDecimal("2.4"), es.marser.tools.MathTools.round(new BigDecimal("2.446564"), 1));
    }

    @Test
    public void channel3() {
        Assert.assertEquals(es.marser.tools.MathTools.parseBigDecimal("45.3"), new BigDecimal("45.3"));
        Assert.assertEquals(es.marser.tools.MathTools.parseBigDecimal("ZZZZZ"), new BigDecimal("0.0"));
        Assert.assertEquals(es.marser.tools.MathTools.parseBigDecimal(null), new BigDecimal("0.0"));
        Assert.assertEquals(es.marser.tools.MathTools.parseInt(null), 0);
        Assert.assertEquals(es.marser.tools.MathTools.parseInt("-1"), -1);
        Assert.assertEquals(es.marser.tools.MathTools.parseInt("ZZZZ"), 0);
        Assert.assertEquals(es.marser.tools.MathTools.parseInt("1.345"), 1);
    }

    @Test
    public void channel4() {
        Assert.assertEquals(true, es.marser.tools.MathTools.areEquals(new BigDecimal("1.325"), new BigDecimal("1.325")));
        Assert.assertEquals(false, es.marser.tools.MathTools.areEquals(new BigDecimal("1.325"), new BigDecimal("1.326")));

        Assert.assertEquals(true, es.marser.tools.MathTools.equalNum(new BigDecimal("1.325"), new BigDecimal("1.326"), 2));
        Assert.assertEquals(true, es.marser.tools.MathTools.equalNum(new BigDecimal("1.325"), "1.326", 2));
        Assert.assertEquals(true, es.marser.tools.MathTools.equalNum("1.325", "1.326", 2));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void channel5(){
        Assert.assertEquals(true, es.marser.tools.MathTools.format(new BigDecimal("100254.325658"), 2).equals("100.254,33"));
        Assert.assertEquals(true, es.marser.tools.MathTools.format("100254.325658", 2).equals("100.254,33"));
        Assert.assertEquals(true, es.marser.tools.MathTools.format(100254.325658, 2).equals("100.254,33"));
        Assert.assertEquals(true, es.marser.tools.MathTools.formatCifra(1, 2).equals("01"));
        Assert.assertEquals(true, es.marser.tools.MathTools.formatCifra(-1, 2).equals("01"));
        Assert.assertEquals(true, es.marser.tools.MathTools.formatPercentaje("-1.093", 2).equals("-109,30%"));
        Assert.assertEquals(true, es.marser.tools.MathTools.formatPercentaje(new BigDecimal("-1.093758"), 2).equals("-109,38%"));
        Assert.assertEquals(true, es.marser.tools.MathTools.formatPercentaje(new BigDecimal(-1.093758), 2).equals("-109,38%"));
        Assert.assertEquals(true, es.marser.tools.MathTools.isMultiple(6,3));
        Assert.assertEquals(false, es.marser.tools.MathTools.isMultiple(6,4));
        Assert.assertEquals(true, es.marser.tools.MathTools.autoNum("001",3).equals("002"));
        Assert.assertEquals(true, es.marser.tools.MathTools.autoNum(null,3).equals("001"));

        Assert.assertEquals(es.marser.tools.MathTools.evalbd("(2-3)*5"), new BigDecimal(-5));
        Assert.assertEquals(es.marser.tools.MathTools.evalbd("6/3+5^2"), new BigDecimal(27));
        Assert.assertEquals(es.marser.tools.MathTools.evalbd("64/(3+5)^2"), new BigDecimal(1));
    }
}
