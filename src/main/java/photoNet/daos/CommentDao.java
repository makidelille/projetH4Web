package photoNet.daos;

import photoNet.exceptions.DaoRuntimeException;
import photoNet.exceptions.PhotoNetRuntimeException;
import photoNet.exceptions.PhotoNotFoundException;
import photoNet.utils.Comment;
import photoNet.utils.Photo;
import photoNet.utils.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julien on 17/12/2016.
 */
public class CommentDao {


    public List<Comment> getCommentsForPhoto(String photoId) throws PhotoNetRuntimeException{
        List<Comment> l = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments INNER JOIN users ON comments.auteur = users.name WHERE photoId = ? ");
            statement.setString(1, photoId);
            try (ResultSet result = statement.executeQuery()){

                while(result.next()){
                    l.add(new Comment()
                            .setId(result.getInt("comments.id"))
                            .setText(result.getString("comments.commentaire"))
                            .setDate(result.getDate("comments.date"))
                            .setColor(result.getString("comments.couleur"))
                            .setAuthor(new Profile().setName("users.name").setProfilPicPath("users.imageProfil"))
                    );
              }
            }
            return l;
        }catch(SQLException e){
            throw new DaoRuntimeException("exception raised in the commentDao",e);
        }



    }



}
