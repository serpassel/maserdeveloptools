package es.marser;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 *         [ES]
 *         Comprobación funcional de DateTools
 *         <p>
 *         [EN]
 *         DateTools Functional Check
 */
public class BooleanToolsTest {

    @Test
    public void channel1() {
        Assert.assertEquals(false, BooleanTools.parseInt(-1));
        Assert.assertEquals(true, BooleanTools.parseInt(0));
        Assert.assertEquals(true, BooleanTools.parseInt(10));
        Assert.assertEquals(true, BooleanTools.parseString("true"));
        Assert.assertEquals(true, BooleanTools.parseString("0"));
        Assert.assertEquals(false, BooleanTools.parseString("false"));
        Assert.assertEquals(false, BooleanTools.parseString("-1"));
        Assert.assertEquals(false, BooleanTools.parseString("CASE"));

        Assert.assertEquals(0, BooleanTools.booleanToInt(true));
        Assert.assertEquals(-1, BooleanTools.booleanToInt(false));
        Assert.assertEquals(-1, BooleanTools.inverseInBoolean(0));
        Assert.assertEquals(0, BooleanTools.inverseInBoolean(-1));
        Assert.assertEquals(true, BooleanTools.nc(null, true));
        Assert.assertEquals(false, BooleanTools.nc(null));

    }
}