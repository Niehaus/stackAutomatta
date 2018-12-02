import java.util.ArrayList;
import java.util.List;

public class State {
	private List<Rule> rules = new ArrayList<Rule>();
	
	
	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
}
