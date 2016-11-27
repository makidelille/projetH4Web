package photoNet.servlets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import photoNet.managers.PhotoManager;

public class ImageServlet extends AbstractServlet{

	public ImageServlet() {
		super("img/photo");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO profil pic too
		int id= Integer.parseInt(request.getParameter("id"));
		
		
		try {
			String path = PhotoManager.getInstance().getImagePath(id);
			Path uri = Paths.get(this.getClass().getClassLoader().getResource("city-no-photo.png").toURI());
			if(path != null){
				uri = Paths.get(path);
			}
			Files.copy(uri, response.getOutputStream());
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected String getTemplateName(HttpServletRequest req) {
		return null;
	}

	@Override
	protected WebContext buildContext(WebContext context, HttpServletRequest req, HttpServletResponse resp) {
		return null;
	}

}
