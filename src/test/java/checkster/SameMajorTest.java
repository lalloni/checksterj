package checkster;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import checkster.rules.SameMajor;

public class SameMajorTest {

    @Test
    public void testEqual() {
        Version v1 = new Version("a", 1, null, null, null);
        Version v2 = new Version("a", 1, null, null, null);
        CheckResult result = new SameMajor().isCompatibleService(v1, v2);
        assertTrue(result.isSuccess());
    }

    @Test
    public void testNotEqual() {
        Version v1 = new Version("a", 1, null, null, null);
        Version v2 = new Version("a", 2, null, null, null);
        CheckResult result = new SameMajor().isCompatibleService(v1, v2);
        assertFalse(result.isSuccess());
    }

    @Test
    public void testFirstNull() {
        Version v1 = new Version("a", null, null, null, null);
        Version v2 = new Version("a", 2, null, null, null);
        CheckResult result = new SameMajor().isCompatibleService(v1, v2);
        assertFalse(result.isSuccess());
    }

    @Test
    public void testSecNull() {
        Version v1 = new Version("a", 1, null, null, null);
        Version v2 = new Version("a", null, null, null, null);
        CheckResult result = new SameMajor().isCompatibleService(v1, v2);
        assertFalse(result.isSuccess());
    }

}
