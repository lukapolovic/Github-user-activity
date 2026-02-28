import java.util.*;

class RepoActivity {
	private String name;
	private List<EventType> listOfEvents;
	private Map<EventType, Integer> numOfActivityPerEventType;

	public RepoActivity(String name) {
		this.name = name;
		this.listOfEvents = new ArrayList<>();
		this.numOfActivityPerEventType = new HashMap<>();
	}

	public String getName() {
		return this.name;
	}

	public List<EventType> getListOfEvents() {
		return this.listOfEvents;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addEvent(EventType event) {
		this.listOfEvents.add(event);
	}

	public void getEventsByEventType() {
		for (EventType event : listOfEvents) {
			numOfActivityPerEventType.merge(event, 1, Integer::sum);
		}
	}

	public String toString() {
		String result = "In " + this.name + " repository:";
		for (Map.Entry<EventType, Integer> entry : numOfActivityPerEventType.entrySet()) {
			result += "\n - The user had " + entry.getValue() + " " + entry.getKey();
		}

		return result + "\n";
	}
}
