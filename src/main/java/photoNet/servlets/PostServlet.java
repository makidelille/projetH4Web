package photoNet.servlets;

import photoNet.service.DataService;
import photoNet.utils.Photo;
import photoNet.utils.Profile;
import photoNet.utils.Ref;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Created by Julien on 18/12/2016.
 */
@MultipartConfig
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch(req.getRequestURI().replace(Ref.CONTEXT,"").replace("/","").replace("post", "")){
            case "login":
                String logName = req.getParameter("name");
                String logPass = req.getParameter("mdp");
                Profile p = DataService.getInstance().getProfile(logName, false);
                if(p != null && p.getHashpass().equals(logPass)){
                    req.getSession().setAttribute(Ref.ATTR_AUTH, p.getName());
                    resp.getWriter().print("ok");
                }else{
                    resp.getWriter().print("nok");
                }
                break;


            case "logout":
                req.getSession().setAttribute(Ref.ATTR_AUTH,"");
                break;
            case "register":
                String regName = req.getParameter("name");
                String regPass = req.getParameter("mdp");
                if(regName != null && regPass != null &&!"".equals(regPass) && !"".equals(regName) && regPass.equals(req.getParameter("mdp_confirm")) && DataService.getInstance().isNameFreeToUser(regName)){
                    if(DataService.getInstance().createNewUser(regName,regPass)) {
                        req.getSession().setAttribute(Ref.ATTR_AUTH, regName);
                        resp.getWriter().print("ok");
                    }else{
                        resp.getWriter().print("nok");
                    }
                }else{
                    resp.getWriter().print("nok");
                }
                break;


            case "add":
                if(!"".equals(req.getSession().getAttribute(Ref.ATTR_AUTH))){
                    String name = req.getParameter("name");
                    String desc = req.getParameter("desc");
                    String author = (String) req.getSession().getAttribute(Ref.ATTR_AUTH);
                    Part figure = req.getPart("image");
                    Photo photo = new Photo().setTitle(name).setAuthor(new Profile().setName(author)).setDesc(desc);
                    photo = DataService.getInstance().addPhoto(photo, figure);
                    if(photo != null){
                        resp.sendRedirect("/pages/media.html?id=" + photo.getId());
                    }
                }else{
                    resp.sendRedirect("/");
                }


            case "edit":


            default:
                resp.sendRedirect("/");
        }
    }


}
