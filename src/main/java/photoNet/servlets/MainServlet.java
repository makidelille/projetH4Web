package photoNet.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import photoNet.utils.Profile;
import photoNet.utils.Ref;

public class MainServlet extends AbstractServlet{

	public MainServlet() {
		super("home");
	}
	
	@Override
	protected WebContext buildContext(WebContext context,HttpServletRequest req, HttpServletResponse resp) {
		context.setVariable(Ref.VAR_PROFILE,(String) req.getSession().getAttribute(Ref.ATTR_AUTH));
		return context;
	}
	
	@Override
	protected String getTemplateName(HttpServletRequest req) {
		return req.getSession().getAttribute(Ref.ATTR_AUTH) != null && !"".equals(req.getSession().getAttribute(Ref.ATTR_AUTH)) ? "index_alt" : "index";
	}
	
}
