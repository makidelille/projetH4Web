package photoNet.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import photoNet.utils.Profile;

public class ProfileDao{

	private static ProfileDao _instance;

	public static ProfileDao getInstance(){
		return _instance = _instance == null ?  new ProfileDao() : _instance;
	}
	
	private ProfileDao(){}
	
	
	public Profile getProfile(String id){
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id=?");
			statement.setString(0, id);
			try (ResultSet result = statement.executeQuery()){
				if(result.first())return new Profile();
				else return null;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
}
