package photoNet.servlets;

import org.thymeleaf.context.WebContext;
import photoNet.service.DataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImagePhotoServlet extends AbstractServlet{

	public ImagePhotoServlet() {
		super("img/photo");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("id");
		try {
			String path = DataService.getInstance().getPhoto(id).getPath();
			Path uri = Paths.get(this.getClass().getClassLoader().getResource("no-photo.png").toURI());
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
