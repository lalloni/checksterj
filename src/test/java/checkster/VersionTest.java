package checkster;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;

public class VersionTest {

    @Test
    public void testToVersionString() {
        assertEquals("1.1.1-pre1+abcd", new Version("a", 1, 1, 1, "pre1", "abcd").toVersionString());
        assertEquals("1.1.1-pre1", new Version("a", 1, 1, 1, "pre1", null).toVersionString());
        assertEquals("1.1.1+abcd", new Version("a", 1, 1, 1, null, "abcd").toVersionString());
        assertEquals("1.1.1", new Version("a", 1, 1, 1, null, null).toVersionString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToServiceVersionString() {
        assertEquals("a-1.1.1-pre1+abcd", new Version("a", 1, 1, 1, "pre1", "abcd").toServiceVersionString());
        assertEquals("a-1.1.1-pre1", new Version("a", 1, 1, 1, "pre1", null).toServiceVersionString());
        assertEquals("a-1.1.1+abcd", new Version("a", 1, 1, 1, null, "abcd").toServiceVersionString());
        assertEquals("a-1.1.1", new Version("a", 1, 1, 1, null, null).toServiceVersionString());
        new Version(null, 1, 1, 1, null, null).toServiceVersionString();
    }

    @Test
    public void testToVersionJSON() {
        assertEquals(JSONValue
                .parse("{\"major\":1,\"minor\":1,\"patch\":1,\"prerelease\":\"pre1\",\"build\":\"abcd\"}").toString(),
                new Version("a", 1, 1, 1, "pre1", "abcd").toVersionJSON().toString());
        assertEquals(JSONValue.parse("{\"major\":1,\"minor\":1,\"patch\":1,\"prerelease\":\"pre1\"}").toString(),
                new Version("a", 1, 1, 1, "pre1", null).toVersionJSON().toString());
        assertEquals(JSONValue.parse("{\"major\":1,\"minor\":1,\"patch\":1,\"build\":\"abcd\"}").toString(),
                new Version("a", 1, 1, 1, null, "abcd").toVersionJSON().toString());
        assertEquals(JSONValue.parse("{\"major\":1,\"minor\":1,\"patch\":1}").toString(), new Version("a", 1, 1, 1,
                null, null).toVersionJSON().toString());
    }

    @Test
    public void testToServiceVersionJSON() {
        assertEquals(
                JSONValue
                        .parse("{\"service\":\"a\",\"major\":1,\"minor\":1,\"patch\":1,\"prerelease\":\"pre1\",\"build\":\"abcd\"}")
                        .toString(), new Version("a", 1, 1, 1, "pre1", "abcd").toServiceVersionJSON().toString());
        assertEquals(JSONValue.parse("{\"service\":\"a\",\"major\":1,\"minor\":1,\"patch\":1,\"prerelease\":\"pre1\"}")
                .toString(), new Version("a", 1, 1, 1, "pre1", null).toServiceVersionJSON().toString());
        assertEquals(JSONValue.parse("{\"service\":\"a\",\"major\":1,\"minor\":1,\"patch\":1,\"build\":\"abcd\"}")
                .toString(), new Version("a", 1, 1, 1, null, "abcd").toServiceVersionJSON().toString());
        assertEquals(JSONValue.parse("{\"service\":\"a\",\"major\":1,\"minor\":1,\"patch\":1}").toString(),
                new Version("a", 1, 1, 1, null, null).toServiceVersionJSON().toString());
    }

    @Test
    public void testFromServiceVersionJSON() {
        assertEquals(
                new Version("a", 1, 1, 1, "pre1", "abcd"),
                Version.fromServiceVersionJSON((JSONObject) JSONValue
                        .parse("{\"service\":\"a\",\"major\":1,\"minor\":1,\"patch\":1,\"prerelease\":\"pre1\",\"build\":\"abcd\"}")));
        assertEquals(new Version("a", 1, 1, 1, "pre1", null), Version.fromServiceVersionJSON((JSONObject) JSONValue
                .parse("{\"service\":\"a\",\"major\":1,\"minor\":1,\"patch\":1,\"prerelease\":\"pre1\"}")));
        assertEquals(new Version("a", 1, 1, 1, null, "abcd"), Version.fromServiceVersionJSON((JSONObject) JSONValue
                .parse("{\"service\":\"a\",\"major\":1,\"minor\":1,\"patch\":1,\"build\":\"abcd\"}")));
        assertEquals(new Version("a", 1, 1, 1, null, null), Version.fromServiceVersionJSON((JSONObject) JSONValue
                .parse("{\"service\":\"a\",\"major\":1,\"minor\":1,\"patch\":1}")));
    }

    @Test
    public void testFromServiceVersionString() {
        assertEquals(new Version("a", 1, 1, 1, "pre1", "abcd"), Version.fromServiceVersionString("a-1.1.1-pre1+abcd"));
        assertEquals(new Version("a", 1, 1, 1, "pre1", null), Version.fromServiceVersionString("a-1.1.1-pre1"));
        assertEquals(new Version("a", 1, 1, 1, null, "abcd"), Version.fromServiceVersionString("a-1.1.1+abcd"));
        assertEquals(new Version("a", 1, 1, 1, null, null), Version.fromServiceVersionString("a-1.1.1"));
        assertEquals(new Version(null, 1, 1, 1, null, null), Version.fromServiceVersionString("1.1.1"));
        assertEquals(new Version(null, 1, 0, 10, null, null), Version.fromServiceVersionString("1.0.10"));
        assertEquals(new Version("algun-servicio", 1, 1, 1, "pre.1.2.3", "abcd.34.a.3"), Version.fromServiceVersionString("algun-servicio-1.1.1-pre.1.2.3+abcd.34.a.3"));
        assertEquals(new Version("algun-ser-vicio", 1, 1, 1, "pre.1.2.3", "abcd.34.a.3"), Version.fromServiceVersionString("algun-ser-vicio-1.1.1-pre.1.2.3+abcd.34.a.3"));
        assertEquals(new Version("algun.ser.vicio", 1, 1, 1, "alpha1", "bcd4453a"), Version.fromServiceVersionString("algun.ser.vicio-1.1.1-alpha1+bcd4453a"));
        assertEquals(new Version(null, 1, 1, 1, "alpha1", "bcd4453a"), Version.fromServiceVersionString("1.1.1-alpha1+bcd4453a"));
    }

}
