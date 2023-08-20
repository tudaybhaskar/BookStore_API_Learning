package apiEngine.model;

import java.util.List;
import java.util.ArrayList;

public class AddBooksRequest {
	
	public String userId ;
	public List<ISBN> collectionOfIsbns ;
	
	public AddBooksRequest(String userId, ISBN isbn) {
		this.userId = userId ;
		collectionOfIsbns = new ArrayList<ISBN>() ;
		collectionOfIsbns.add(isbn) ;
	}

}
