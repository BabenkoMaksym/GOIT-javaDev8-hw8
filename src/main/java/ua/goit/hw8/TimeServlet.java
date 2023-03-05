package ua.goit.hw8;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebServlet(urlPatterns = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer zone = timezoneParser(req);

        dateWithTimezone(zone, resp);

    }

    private HttpServletResponse dateWithTimezone(Integer zone, HttpServletResponse resp) throws IOException {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        String nowStr = format.format(date);

        if (zone != null) {
            if (zone >= -12 & zone <= 12) {
                long msDate = date.getTime() + zone * 3600000;
                date = new Date(msDate);
            } else {
                resp.setStatus(400);
                resp.getWriter().write("Invalid timezone");
                resp.getWriter().close();
                return resp;
            }

            if (zone > 0) {
                nowStr = nowStr + "+" + zone;
            } else {
                nowStr = nowStr + zone;
            }
        }

        resp.setHeader("Content-Type", "text/html; charset=utf-8");
        resp.getWriter().write(nowStr + "<br>");
        resp.getWriter().close();
        return resp;
    }

    private Integer timezoneParser(HttpServletRequest req) {
        Integer result = null;
        String timezone = req.getParameter("timezone");
        if (timezone != null) {
            String substring = timezone.substring(3);
            if (substring.startsWith(" ")) {
                substring = substring.substring(1);
            }
            result = Integer.parseInt(substring);
        }
        return result;
    }
}
