package pojo;

public class LoginRequest {
	
	private String email;
	private String password;
	
	//Default constructor 
	public LoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	// Public getter setter methods
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDetails [email=" + email + ", password=" + password + "]";
	}
}
