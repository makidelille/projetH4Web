package photoNet.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Julien on 19/12/2016.
 */

public class RedirectFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String uri = ((HttpServletRequest)req).getRequestURI();
        if(uri.endsWith(".html") && !uri.contains("/pages/")){
            ((HttpServletResponse)resp).sendRedirect("/photoNet");
        }else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
