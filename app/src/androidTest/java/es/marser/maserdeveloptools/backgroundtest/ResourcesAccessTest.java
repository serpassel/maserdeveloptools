package es.marser.maserdeveloptools.backgroundtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.systemtools.ResourcesAccess;
import es.marser.tools.SystemColor;

/**
 * @author sergio
 *         Created by sergio on 3/11/17.
 */
@SuppressWarnings("unused")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ResourcesAccessTest {

    private String name = "national_holidays_2018";
    private String nameError = "nh";
    private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void channe1() {
        int res = ResourcesAccess.getResId(name, es.marser.backgroundtools.R.array.class);
        runList(context.getResources().getStringArray(res));
    }

    @Test
    public void error1() {
        try {
            int res = ResourcesAccess.getResId(nameError, es.marser.backgroundtools.R.array.class);
            runList(context.getResources().getStringArray(res));
        }catch (android.content.res.Resources.NotFoundException ignored){

        }
    }

    @Test
    public void channel2() {
        runList(context.getResources().getStringArray(ResourcesAccess.getResArrayId(context, name)));
    }

    @Test
    public void error2() {
        int res = ResourcesAccess.getResArrayId(context, nameError);
        String[] in = context.getResources().getStringArray(res);
        runList(context.getResources().getStringArray(res));
    }

    @Test
    public void channel3(){
        Assert.assertEquals(ResourcesAccess.getNatinoalHolidaysFilter(context, 2018), "01012018|30032018|01052018|15082018|12102018|01112018|06122018|08122018|25122018|");
        Assert.assertEquals(ResourcesAccess.getAutonomyHolidaysFilter(context, 2018), "06012018|28022018|01032018|19032018|29032018|02042018|23042018|02052018|17052018|30052018|31052018|09062018|25072018|28072018|22082018|08092018|11092018|15092018|09102018|26122018|");
        Assert.assertEquals(ResourcesAccess.getNatinoalHolidaysFilter(context, 9999),"");
        Assert.assertEquals(ResourcesAccess.getItemId(context, name, "30032018"), 1);
        //String[] in = ResourcesAccess.getAutonomyHolidays(context, 2018);
        //Log.i(LOG_TAG.TAG, ResourcesAccess.getAutonomyHolidaysFilter(context, 2018));
    }

    private void runList(String[] in){
        for (String s: in){
            Log.d(LOG_TAG.TAG, "Recurso " + s);
            System.out.println(SystemColor.ANSI_GREEN + s);
        }
    }
}
