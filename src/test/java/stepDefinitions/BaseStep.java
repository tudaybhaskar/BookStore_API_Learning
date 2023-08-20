package stepDefinitions;

import apiEngine.EndPoints_InstanceMethods;
import cucumberHelper.TestContext;

public class BaseStep {
	
	private static final String BASE_URL = "https://demoqa.com" ;
	private EndPoints_InstanceMethods endPoints ;
	
	//Constructor
	public BaseStep(TestContext testContext) {
		endPoints = testContext.getEndPoints() ;
	}
	
	public EndPoints_InstanceMethods getEndPoints() {
		return endPoints;
	}

}
