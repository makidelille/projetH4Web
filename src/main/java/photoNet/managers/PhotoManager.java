package photoNet.managers;

public class PhotoManager {

	private static PhotoManager _instance;
	
	public static PhotoManager getInstance(){
		return _instance = (_instance != null ? _instance : new PhotoManager());
	}
	
	private PhotoManager(){}

	public String getImagePath(int id) {
		return null;
	}
	
}
