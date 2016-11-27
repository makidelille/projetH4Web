package photoNet.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import photoNet.daos.ProfileDao;
import photoNet.utils.Profile;
import photoNet.utils.Ref;

/**
 * Servlet implementation class PageHomeServlet
 */
public class PagesServlets extends AbstractServlet{

	private interface IContextBuilder{
		WebContext build(WebContext old, HttpServletRequest req);
		
		public static final IContextBuilder DEFAULT = new IContextBuilder() {
			
			@Override
			public WebContext build(WebContext old, HttpServletRequest req) {
				return old;
			}
		};
	}
	
	private Map<String,IContextBuilder> mapping;
	
	
	public PagesServlets() {
		super("pages/*");
		mapping = new HashMap<>();
		mapping.put("home", IContextBuilder.DEFAULT);
		mapping.put("about", IContextBuilder.DEFAULT);
		mapping.put("connection",IContextBuilder.DEFAULT);
		mapping.put("deconnnection", IContextBuilder.DEFAULT);
		mapping.put("add", IContextBuilder.DEFAULT);
		mapping.put("profile", new IContextBuilder() {
			
			@Override
			public WebContext build(WebContext old, HttpServletRequest req) {
				String id = req.getParameter("name");
				Profile p = !"".equals(id) ? ProfileDao.getInstance().getProfile(id) :Profile.NOT_FOUND;			
				old.setVariable(Ref.VAR_PROFILE, p);
				return old;
			}
		});
	}

	private String convertToLocalName(String url){
		return url.replace("/photoNet/pages/", "").replace(".html", "");
	}
	

	@Override
	protected String getTemplateName(HttpServletRequest req) {
		String local = convertToLocalName(req.getRequestURI());
		if(mapping.keySet().contains(local)){
			return "pages/" +local;
		}
		return "pages/404";
	}


	@Override
	protected WebContext buildContext(WebContext context, HttpServletRequest req, HttpServletResponse resp) {
		String local = convertToLocalName(req.getContextPath());
		return mapping.keySet().contains(local) ? mapping.get(local).build(context, req) : context;
	}

	
}
