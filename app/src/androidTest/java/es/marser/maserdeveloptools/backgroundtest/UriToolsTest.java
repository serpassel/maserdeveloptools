package es.marser.maserdeveloptools.backgroundtest;

import android.net.Uri;

import org.junit.Assert;
import org.junit.Test;

import es.marser.backgroundtools.enums.SystemExtras;
import es.marser.backgroundtools.systemtools.UriTools;

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
        Assert.assertEquals(SystemExtras.URI_FROM_GMAIL, UriTools.locateUriProvider(uri));
    }
}