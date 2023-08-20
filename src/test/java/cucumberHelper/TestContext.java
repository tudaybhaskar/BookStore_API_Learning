package cucumberHelper;

import apiEngine.EndPoints_InstanceMethods;
import enums.Context;

public class TestContext {
	
	private String BASE_URL = "https://demoqa.com" ;
	private EndPoints_InstanceMethods endPoints ;
	private ScenarioContext scenarioContext ;
	private final String USER_ID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
	
	//Constructor
	public TestContext() {
		endPoints = new EndPoints_InstanceMethods(BASE_URL) ;	
		scenarioContext = new ScenarioContext() ;
		scenarioContext.setContext(Context.USER_ID, USER_ID);
	}
	
	public EndPoints_InstanceMethods getEndPoints() {
		return endPoints;
		
	}
	
	public ScenarioContext getScenarioContext() {
		return scenarioContext ;
	}

}
