package es.marser;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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
        MathToolsTest.class
})
public class StaticSuiteTest {

}
