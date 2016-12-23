package photoNet.daos;

import photoNet.exceptions.CantAddCommentException;
import photoNet.exceptions.CantAddPhotoException;
import photoNet.exceptions.DaoRuntimeException;
import photoNet.exceptions.PhotoNetRuntimeException;
import photoNet.utils.Comment;
import photoNet.utils.Profile;

import java.sql.*;
import java.util.ArrayList;
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


    public int addNewComment(String author, int photoId, String comment, Date date) throws PhotoNetRuntimeException {
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO comments(auteur,photoId,commentaire,date) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,author);
            statement.setInt(2, photoId);
            statement.setString(3,comment);
            statement.setDate(4,date);
            int rows = statement.executeUpdate();
            if(rows == 0){
                throw new CantAddCommentException("no rows affected");
            }
            try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                }else{
                    throw new CantAddCommentException("failed to get id");
                }
            }
        } catch (SQLException e) {
            throw new DaoRuntimeException("excetpiton raised in the commentDao",e);
        }
    }

    public int addNewCommentTo(String author, int photoId, String comment, Date date, int rep) throws PhotoNetRuntimeException{
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO comments(auteur,photoId,commentaire,date, reponse) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,author);
            statement.setInt(2, photoId);
            statement.setString(3,comment);
            statement.setDate(4,date);
            statement.setInt(5,rep);
            int rows = statement.executeUpdate();
            if(rows == 0){
                throw new CantAddCommentException("no rows affected");
            }
            try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                }else{
                    throw new CantAddCommentException("failed to get id");
                }
            }
        } catch (SQLException e) {
            throw new DaoRuntimeException("excetpiton raised in the photoDao",e);
        }
    }
}
