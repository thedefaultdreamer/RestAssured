package pojo;

import java.util.List;

public class SignupResponse {

	private List<Errors> errors;


	public List<Errors> getErrors() {
		return errors;
	}


	public void setErrors(List<Errors> errors) {
		this.errors = errors;
	}


	public static class Errors {
		
		public Errors() {}

		private String message;
		
		private String type;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}
	
}
