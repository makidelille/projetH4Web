package photoNet.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import photoNet.utils.Ref;

public class AttrFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
		HttpSession session = ((HttpServletRequest)request).getSession();
		if(session.getAttribute(Ref.ATTR_AUTH) == null){
			session.setAttribute(Ref.ATTR_AUTH, "");
		}
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {}


}
