package es.marser.maserdeveloptools;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import es.marser.maserdeveloptools.backgroundtest.DialogTest;
import es.marser.maserdeveloptools.backgroundtest.LaunchToastTest;
import es.marser.maserdeveloptools.sqltools.CRUDHandlerTest;
import es.marser.sqltools.SQLStrings;

/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DialogTest.class,
        LaunchToastTest.class,
        CRUDHandlerTest.class
})
public class AndroidSuiteTest {

}
