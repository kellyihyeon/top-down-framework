import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws Exception {
        final Server server = new Server(12345);
        server.setHandler(new TestHandler());
        server.start();
        server.join();
    }
}

class TestHandler extends SessionHandler {

    @Override
    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println(request.getRequestURI());
        baseRequest.setHandled(true);
    }
}
