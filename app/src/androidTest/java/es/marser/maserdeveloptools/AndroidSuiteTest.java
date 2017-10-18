package es.marser.maserdeveloptools;


import android.support.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import es.marser.maserdeveloptools.backgroundtest.DialogTest;
import es.marser.maserdeveloptools.backgroundtest.LaunchToastTest;
import es.marser.maserdeveloptools.sqltools.CRUDHandlerTest;
import es.marser.maserdeveloptools.sqltools.CRUDHandlerTest2;

/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 */
@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DialogTest.class,
        LaunchToastTest.class,
        CRUDHandlerTest.class,
        CRUDHandlerTest2.class
})
public class AndroidSuiteTest {

}
