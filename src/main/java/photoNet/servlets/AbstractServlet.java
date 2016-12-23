package photoNet.servlets;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public abstract class AbstractServlet extends HttpServlet{

	private String name;

	public AbstractServlet(String name) {
		this.name = name;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("serving :  "+ req.getRequestURI());
		Locale.setDefault(Locale.FRENCH);
		resp.setCharacterEncoding("UTF-8");
		this.createTemplateEngine(req).process(getTemplateName(req), this.buildContext(new WebContext(req, resp, req.getServletContext()), req,resp), resp.getWriter());
		
	}
	
	protected abstract String getTemplateName(HttpServletRequest req);

	protected abstract WebContext buildContext(WebContext context,HttpServletRequest req, HttpServletResponse resp);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(getServletName());
	}	
	
	@Override
	public String getServletName() {
		return this.name;
	}
	
	protected TemplateEngine createTemplateEngine(HttpServletRequest request) {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(request.getServletContext());
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(new Java8TimeDialect());

		
		return templateEngine;
	}
}
