package in.shareapp.utils;

import com.fasterxml.jackson.jr.ob.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ServletUtil {

    public static void writeJsonResponse(HttpServletResponse response, Map<String, Object> jsonResponse, int statusCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(JSON.std
                .with(JSON.Feature.PRETTY_PRINT_OUTPUT)
                .asString(jsonResponse));
    }
}
