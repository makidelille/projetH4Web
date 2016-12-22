package photoNet.servlets;

import org.thymeleaf.context.WebContext;
import photoNet.service.DataService;
import photoNet.utils.Ref;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ImageServlet extends AbstractServlet{

	public ImageServlet() {
		super("image/*");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getRequestURI().replace(Ref.CONTEXT,"").replace("/","").replace("image", "").startsWith("photo")) {
			String photoId = request.getParameter("id");
				String path = DataService.getInstance().getPhoto(photoId).getPath();
				if (path == null) {
					InputStream is = getClass().getClassLoader().getResourceAsStream("no-photo.png");
					int len;
					byte[] bb = new byte[2048];
					while((len = is.read(bb)) != -1){
						response.getOutputStream().write(bb,0,len);
					}
				}else {
					Files.copy(Paths.get(path), response.getOutputStream());
				}
		}else if(request.getRequestURI().replace(Ref.CONTEXT,"").replace("/","").replace("image", "").startsWith("profil")){
			String profilId = request.getParameter("id");
			String path = DataService.getInstance().getProfile(profilId,false).getProfilPicPath();
			if (path == null) {
                InputStream is = getClass().getClassLoader().getResourceAsStream("no-user.png");
				int len;
				byte[] bb = new byte[2048];
				while((len = is.read(bb)) != -1){
					response.getOutputStream().write(bb,0,len);
				}
            }else {
				Files.copy(Paths.get(path), response.getOutputStream());
			}

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
