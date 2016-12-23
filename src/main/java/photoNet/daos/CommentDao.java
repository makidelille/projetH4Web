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
                            .setId(result.getInt("id"))
                            .setText(result.getString("commentaire"))
                            .setDate(result.getDate("date").toLocalDate())
                            .setColor(result.getString("couleur"))
                            .setAuthor(new Profile().setName(result.getString("auteur")));
                    int ans = result.getInt("reponse");
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


    public int addNewComment(String author, int photoId, String comment, Date date, String color) throws PhotoNetRuntimeException {
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO comments(auteur,photoId,commentaire,date,couleur) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,author);
            statement.setInt(2, photoId);
            statement.setString(3,comment);
            statement.setDate(4,date);
            statement.setString(5,color);
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

    public int addNewCommentTo(String author, int photoId, String comment, Date date, String color, int rep) throws PhotoNetRuntimeException{
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO comments(auteur,photoId,commentaire,date,couleur, reponse) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,author);
            statement.setInt(2, photoId);
            statement.setString(3,comment);
            statement.setDate(4,date);
            statement.setString(5,color);
            statement.setInt(6,rep);
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

    public boolean deleteCommentsPhoto(String photoId) throws PhotoNetRuntimeException{
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE photoId = ?");
            statement.setString(1,photoId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw  new DaoRuntimeException("excetpiton raised in the photoDao",e);
        }
    }
}
