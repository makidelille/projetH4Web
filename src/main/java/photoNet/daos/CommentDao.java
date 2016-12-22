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
import java.util.Collections;
import java.util.List;

/**
 * Created by Julien on 17/12/2016.
 */
public class CommentDao {


    public List<Comment> getCommentsForPhoto(String photoId) throws PhotoNetRuntimeException{
        List<Comment> l = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments WHERE photoId = ? ORDER BY comments.id ASC");
            statement.setString(1, photoId);
            try (ResultSet result = statement.executeQuery()){

                while(result.next()){
                    Comment c = new Comment()
                            .setId(result.getInt("comments.id"))
                            .setText(result.getString("comments.commentaire"))
                            .setDate(result.getDate("comments.date"))
                            .setColor(result.getString("comments.couleur"))
                            .setAuthor(new Profile().setName("users.name"));
                    int ans = result.getInt("comments.response");
                    if(!result.wasNull()){
                        //c'est une réponse
                        for(Comment cm : l) if(cm.getId() == ans) cm.addResponse(c);
                    }else {
                        l.add(c);
                    }

              }
            }
            return l;
        }catch(SQLException e){
            throw new DaoRuntimeException("exception raised in the commentDao",e);
        }



    }



}
