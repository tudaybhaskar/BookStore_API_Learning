package cucumberHelper;

import apiEngine.EndPoints_InstanceMethods;

public class TestContext {
	
	private String BASE_URL = "https://demoqa.com" ;
	private EndPoints_InstanceMethods endPoints ;
	
	//Constructor
	public TestContext() {
		endPoints = new EndPoints_InstanceMethods(BASE_URL) ;	
	}
	
	public EndPoints_InstanceMethods getEndPoints() {
		return endPoints;
		
	}

}
