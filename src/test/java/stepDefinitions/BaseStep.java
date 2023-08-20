package stepDefinitions;

import apiEngine.EndPoints_InstanceMethods;
import cucumberHelper.ScenarioContext;
import cucumberHelper.TestContext;

public class BaseStep {
	
	private EndPoints_InstanceMethods endPoints ;
	private ScenarioContext scenarioContext ;
	
	//Constructor
	public BaseStep(TestContext testContext) {
		endPoints = testContext.getEndPoints() ;
		scenarioContext = testContext.getScenarioContext() ;
	}
	
	public EndPoints_InstanceMethods getEndPoints() {
		return endPoints;
	}
	
	public ScenarioContext getScenarioContext() {
		return scenarioContext ;
	}

}
