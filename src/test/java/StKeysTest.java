import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class StKeysTest {

    @Test
    public void testCrack() throws Exception {
        StKeys stKeys = new StKeys();
        stKeys.setStartYear(2010);
        stKeys.setEndYear(2010);
        stKeys.setEndCharsetLength(1);

        Collection<String> keys = stKeys.crack("8F42BA");
        
        Assert.assertTrue(keys.contains("B4474D9CEB"));
    }

    @Test
    public void toSha1() throws Exception {
        StKeys stKeys = new StKeys();

        String hash = stKeys.toSha1("1234567890");

        Assert.assertEquals("01b307acba4f54f55aafc33bb06bbbf6ca803e9a",hash.toLowerCase());
    }
}