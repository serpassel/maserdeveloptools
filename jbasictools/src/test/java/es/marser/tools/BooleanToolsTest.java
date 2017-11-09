package es.marser.tools;

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
@SuppressWarnings({"unused", "WeakerAccess"})
public class BooleanToolsTest {

    @Test
    public void channel1() {
        Assert.assertEquals(false, es.marser.tools.BooleanTools.parseInt(-1));
        Assert.assertEquals(true, es.marser.tools.BooleanTools.parseInt(0));
        Assert.assertEquals(true, es.marser.tools.BooleanTools.parseInt(10));
        Assert.assertEquals(true, es.marser.tools.BooleanTools.parseString("true"));
        Assert.assertEquals(true, es.marser.tools.BooleanTools.parseString("0"));
        Assert.assertEquals(false, es.marser.tools.BooleanTools.parseString("false"));
        Assert.assertEquals(false, es.marser.tools.BooleanTools.parseString("-1"));
        Assert.assertEquals(false, es.marser.tools.BooleanTools.parseString("CASE"));

        Assert.assertEquals(0, es.marser.tools.BooleanTools.booleanToInt(true));
        Assert.assertEquals(-1, es.marser.tools.BooleanTools.booleanToInt(false));
        Assert.assertEquals(-1, es.marser.tools.BooleanTools.inverseInBoolean(0));
        Assert.assertEquals(0, es.marser.tools.BooleanTools.inverseInBoolean(-1));
        Assert.assertEquals(true, es.marser.tools.BooleanTools.nc(null, true));
        Assert.assertEquals(false, es.marser.tools.BooleanTools.nc(null));

    }
}