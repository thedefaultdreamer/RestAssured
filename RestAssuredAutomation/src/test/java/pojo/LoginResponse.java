package pojo;

public class LoginResponse {

	// Declaring response variable as private field
	private String access_token;
	private String id_token;
	private String refresh_token;
	private String session;

	//Public Constructor
	public LoginResponse()
	{
		
	}

	// Public getter setter methods
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getId_token() {
		return id_token;
	}

	public void setId_token(String id_token) {
		this.id_token = id_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
	
	@Override
	public String toString() {
		return "LoginResponse [access_token=" + access_token + ", id_token=" + id_token + ", refresh_token="
				+ refresh_token + ", session=" + session + "]";
	}

}
