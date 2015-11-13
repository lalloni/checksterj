package checkster;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.Test;

public class ChecksterTest {

    @Test
    public void testCheckResult() throws Exception {
        Server s = startServer(SC_OK, "bla", 3);
        try {
            Check check = new Check(new Version("bla", 3, 1, 1, null), url(s));
            CheckResult result = check.getResult();
            assertTrue(result.isSuccess());
        } finally {
            s.stop();
        }
    }

    @Test
    public void testFailMajor() throws Exception {
        Server s = startServer(SC_OK, "bla", 2);
        try {
            Check check = new Check(new Version("bla", 3, 1, 1, null), url(s));
            CheckResult result = check.getResult();
            assertFalse(result.isSuccess());
            assertTrue(result.getReason().getMessage().contains("mayor"));
        } finally {
            s.stop();
        }
    }

    @Test
    public void testFailService() throws Exception {
        Server s = startServer(SC_OK, "bla", 2);
        try {
            Check check = new Check(new Version("ble", 3, 1, 1, null), url(s));
            CheckResult result = check.getResult();
            assertFalse(result.isSuccess());
            assertTrue(result.getReason().getMessage().contains("identificador del servicio"));
        } finally {
            s.stop();
        }
    }

    @Test
    public void testFailStatus() throws Exception {
        Server s = startServer(SC_BAD_REQUEST, "bla", 2);
        try {
            Check check = new Check(new Version("ble", 3, 1, 1, null), url(s));
            CheckResult result = check.getResult();
            assertFalse(result.isSuccess());
            assertTrue(result.getReason().getMessage().contains("HTTP"));
        } finally {
            s.stop();
        }
    }

    @Test
    public void testFailConnect() throws Exception {
        Check check = new Check(new Version("ble", 3, 1, 1, null), new URL("http://lalomadelalora.com.pirulo"));
        CheckResult result = check.getResult();
        assertFalse(result.isSuccess());
        assertTrue(result.getReason().getMessage().contains("conectar"));
    }

    @Test(expected = CheckFailure.class)
    public void testFailThrows() throws Exception {
        new Check(new Version("ble", 3, 1, 1, null), new URL("http://lalomadelalora.com.pirulo")).ensureSuccessful();
    }

    private URL url(Server s) throws MalformedURLException {
        return new URL("http://localhost:" + port(s));
    }

    private int port(Server s) {
        return ((ServerConnector) s.getConnectors()[0]).getLocalPort();
    }

    private Server startServer(final int sc, final String service, final int major) throws Exception {
        Server s = new Server(7707);
        s.setHandler(new AbstractHandler() {
            public void handle(String target, Request baseRequest, HttpServletRequest request,
                    HttpServletResponse response) throws IOException, ServletException {
                response.setStatus(sc);
                response.getOutputStream().println(
                        "{\"service\":\"" + service + "\",\"major\":" + major
                                + ",\"minor\":0,\"patch\":1,\"meta\":\"build1\"}");
                response.getOutputStream().close();
            }
        });
        s.start();
        return s;
    }
}
