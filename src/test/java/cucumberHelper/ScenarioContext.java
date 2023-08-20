package cucumberHelper;

import java.util.Map;
import java.util.HashMap;
import enums.Context;
public class ScenarioContext {
	
	private Map<String, Object> scenarioContext ;
	
	public ScenarioContext() {
		scenarioContext = new HashMap<String, Object>() ;
	}
	
	public void setContext(Context Key, Object value)  {
		scenarioContext.put(Key.toString(), value) ;
	}
	
	public Object getContext(Context Key) {
		return scenarioContext.get(Key.toString() ) ;
	}
	
	public Boolean isContains(Context Key) {
		return scenarioContext.containsKey( Key.toString() );
		
	}
}
