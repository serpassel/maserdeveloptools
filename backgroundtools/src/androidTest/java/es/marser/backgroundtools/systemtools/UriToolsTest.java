package es.marser.backgroundtools.systemtools;

import android.net.Uri;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sergio
 *         Created by sergio on 6/10/17.
 */
@SuppressWarnings("unused")
public class UriToolsTest {

    @Test
    public void channel1(){
        Uri uri = Uri.parse( "http://gmail.com" );
        Assert.assertEquals(true, UriTools.isValid(uri));
        Assert.assertEquals(UriTools.FROM_GMAIL, UriTools.locateUriProvider(uri));
    }
}