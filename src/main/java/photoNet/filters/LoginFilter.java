package photoNet.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import photoNet.utils.Ref;

public class LoginFilter extends AbstractFilter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		if(session.getAttribute(Ref.ATTR_AUTH) != null && !"".equals(session.getAttribute(Ref.ATTR_AUTH))){
			request.setAttribute(Ref.ATTR_AUTH, session.getAttribute(Ref.ATTR_AUTH));
		}
		
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {}


}
