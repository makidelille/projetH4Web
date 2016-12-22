package photoNet.servlets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.thymeleaf.context.WebContext;

import photoNet.daos.PhotoDao;
import photoNet.daos.ProfileDao;
import photoNet.service.DataService;
import photoNet.utils.Comment;
import photoNet.utils.Photo;
import photoNet.utils.Profile;
import photoNet.utils.Ref;

/**
 * Servlet implementation class PageHomeServlet
 */
public class PagesServlets extends AbstractServlet{

	private interface IContextBuilder{
		WebContext build(WebContext old, HttpServletRequest req);
		
		IContextBuilder DEFAULT = new IContextBuilder() {

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
		mapping.put("deconnection", IContextBuilder.DEFAULT);
		mapping.put("add", IContextBuilder.DEFAULT);
		mapping.put("profil", new IContextBuilder() {

			@Override
			public WebContext build(WebContext old, HttpServletRequest req) {
				String id = req.getParameter("name");
				Profile p = DataService.getInstance().getProfile(id, true);
				old.setVariable(Ref.VAR_PROFILE, p);
				return old;
			}
		});
		mapping.put("myprofil", new IContextBuilder() {
			@Override
			public WebContext build(WebContext old, HttpServletRequest req) {
				String id = (String) req.getSession().getAttribute(Ref.ATTR_AUTH);
				Profile p = DataService.getInstance().getProfile(id,true);
				old.setVariable(Ref.VAR_PROFILE,p);
				return old;
			}
		});
		mapping.put("media", new IContextBuilder() {
			@Override
			public WebContext build(WebContext old, HttpServletRequest req) {
				String photoId = req.getParameter("photo");
				Photo p = DataService.getInstance().getPhoto(photoId);
				old.setVariable(Ref.VAR_PHOTO, p);
				List<Comment> comments = DataService.getInstance().getCommentsForPhoto(photoId);
				old.setVariable(Ref.VAR_COMMENTS, comments);
				return old;
			}
		});

	}

	private String convertToLocalName(String url){
		return url.replace(Ref.CONTEXT,"").replace("/","").replace("pages", "").replace(".html", "");
	}
	

	@Override
	protected String getTemplateName(HttpServletRequest req) {
		String local = convertToLocalName(req.getRequestURI());
		if(local.equals("myprofil")) return "pages/profil";
		if(mapping.keySet().contains(local)){
			return "pages/" +local;
		}
		return "pages/404";
	}


	@Override
	protected WebContext buildContext(WebContext context, HttpServletRequest req, HttpServletResponse resp) {
		String local = convertToLocalName(req.getRequestURI());
		return mapping.keySet().contains(local) ? mapping.get(local).build(context, req) : context;
	}

	
}
