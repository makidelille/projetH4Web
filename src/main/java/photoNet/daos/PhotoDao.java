package photoNet.daos;

public class PhotoDao {

	
	private static PhotoDao _instance;

	public static PhotoDao getInstance(){
		return _instance = _instance == null ?  new PhotoDao() : _instance;
	}
	
	private PhotoDao(){}
	
	
}
