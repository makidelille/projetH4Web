package photoNet.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import photoNet.utils.Ref;

public class MainServlet extends AbstractServlet{

	public MainServlet() {
		super("home");
	}
	
	@Override
	protected WebContext buildContext(WebContext context,HttpServletRequest req, HttpServletResponse resp) {
		if(!"".equals(req.getAttribute(Ref.ATTR_AUTH))){
			context.setVariable(Ref.VAR_NAME, req.getAttribute(Ref.ATTR_AUTH));
		}
		
		return context;
	}
	
	@Override
	protected String getTemplateName(HttpServletRequest req) {
		return req.getAttribute(Ref.ATTR_AUTH) != null && !"".equals(req.getAttribute(Ref.ATTR_AUTH)) ? "index_alt" : "index";
	}
	
}
