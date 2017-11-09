package es.marser.tools;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import es.marser.generic.GenericFactoryTest;

/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 *         Suite de tests estáticos
 *         [EN] Static test suite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TextToolsTest.class,
        DateToolsTest.class,
        BooleanToolsTest.class,
        MathToolsTest.class,
        GenericFactoryTest.class
})
public class StaticSuiteTest {

}
