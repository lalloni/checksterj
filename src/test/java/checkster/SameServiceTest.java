package checkster;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import checkster.rules.SameService;

public class SameServiceTest {

    @Test
    public void testEqual() {
        Version v1 = new Version("a", 0, 0, 0, null, null);
        Version v2 = new Version("a", 0, 0, 0, null, null);
        CheckResult result = new SameService().isCompatibleService(v1, v2);
        assertTrue(result.isSuccess());
    }

    @Test
    public void testNotEqual() {
        Version v1 = new Version("a", 0, 0, 0, null, null);
        Version v2 = new Version("b", 0, 0, 0, null, null);
        CheckResult result = new SameService().isCompatibleService(v1, v2);
        assertFalse(result.isSuccess());
    }

    @Test
    public void testFirstNull() {
        Version v1 = new Version(null, 0, 0, 0, null, null);
        Version v2 = new Version("b", 0, 0, 0, null, null);
        CheckResult result = new SameService().isCompatibleService(v1, v2);
        assertFalse(result.isSuccess());
    }

    @Test
    public void testSecNull() {
        Version v1 = new Version("a", 0, 0, 0, null, null);
        Version v2 = new Version(null, 0, 0, 0, null, null);
        CheckResult result = new SameService().isCompatibleService(v1, v2);
        assertFalse(result.isSuccess());
    }

}
