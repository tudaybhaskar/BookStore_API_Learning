package apiEngine.model;

public class DeleteBookRequest {
	
	public String isbn ;
	public String userId ;
	
	public DeleteBookRequest(String isbn, String userId) {
		this.isbn = isbn ;
		this.userId = userId ;
	}

}
