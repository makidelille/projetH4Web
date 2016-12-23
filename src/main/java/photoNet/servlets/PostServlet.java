package photoNet.servlets;

import photoNet.daos.CommentDao;
import photoNet.service.DataService;
import photoNet.utils.Comment;
import photoNet.utils.Photo;
import photoNet.utils.Profile;
import photoNet.utils.Ref;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Julien on 18/12/2016.
 */
@MultipartConfig
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(Ref.CONTEXT);
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
                resp.sendRedirect(Ref.CONTEXT);
                break;
            case "register":
                String regName = req.getParameter("name");
                String regPass = req.getParameter("mdp");
                if(regName != null && regPass != null &&!"".equals(regPass) && !"".equals(regName) && regPass.equals(req.getParameter("mdp_confirm")) && DataService.getInstance().isNameFreeToUser(regName)){
                    if(!DataService.getInstance().isNameFreeToUser(regName)){
                        resp.getWriter().print("taken");
                    }else if(DataService.getInstance().createNewUser(regName,regPass)) {
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
                    String name = req.getParameter("titre");
                    String desc = req.getParameter("desc");
                    String author = (String) req.getSession().getAttribute(Ref.ATTR_AUTH);
                    Part figure = req.getPart("file");
                    Photo photo = new Photo().setTitle(name).setAuthor(new Profile().setName(author)).setDesc(desc);
                    photo = DataService.getInstance().addPhoto(photo, figure);
                    if(photo != null){
                        resp.sendRedirect(Ref.CONTEXT + "/media.html?id=" + photo.getId());
                    }else{
                        resp.getWriter().print("couldn't add photo");
                    }
                }else{
                    resp.getWriter().print("log in first");
                }
                break;
            case "comment":
                if(!"".equals(req.getSession().getAttribute(Ref.ATTR_AUTH))){
                    int photoId = Integer.parseInt(req.getParameter("photoId"));
                    String comment = req.getParameter("comment");
                    LocalDate time = LocalDate.now();
                    String author = (String) req.getSession().getAttribute(Ref.ATTR_AUTH);
                    Comment c = new Comment().setAuthor(new Profile().setName(author)).setDate(time).setText(comment).setPhoto(new Photo().setId(photoId));
                    int rep = -1;
                    if(req.getParameter("response") != null){
                        rep = Integer.parseInt(req.getParameter("response"));
                    }
                    c = DataService.getInstance().addComment(c,rep);
                    if(c != null) resp.getOutputStream().print("ok");
                    else resp.getOutputStream().print("log in first");

                }else{
                    resp.sendRedirect(Ref.CONTEXT);
                }
                break;
            case "edit":
                if(!"".equals(req.getSession().getAttribute(Ref.ATTR_AUTH))) {
                    String nameId = req.getParameter("name");
                    if (req.getSession().getAttribute(Ref.ATTR_AUTH).equals(nameId)) {
                        String desc = req.getParameter("profilDesc");
                        Part profilePic = req.getPart("img");
                        Profile prof = DataService.getInstance().getProfile(nameId, false);
                        prof.setDesc(desc);
                        DataService.getInstance().updateProfile(prof, profilePic);

                    }
                }
                resp.sendRedirect(Ref.CONTEXT);
                break;

            default:
                resp.sendRedirect(Ref.CONTEXT);
        }
    }


}
