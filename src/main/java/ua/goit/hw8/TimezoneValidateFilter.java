package ua.goit.hw8;

import jakarta.servlet.http.HttpServletRequest;

public class TimezoneValidateFilter {
    public Integer filter(HttpServletRequest req) {
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
