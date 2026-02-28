import java.util.Arrays;

class UserEvent {
	private int id;
	private String type;
	private String repo;

	public UserEvent(int id, String type, String repo) {
		this.id = id;
		this.type = type;
		this.repo = repo;
	}

	public int getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}

	public String getRepo() {
		return this.repo;
	}

	public void setId(int id) {
		if (id > 0) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("Id must be greater than 0!");
		}
	}

	public void setType(String type) {
		String[] acceptedEvents = { "WatchEvent", "CreateEvent", "PushEvent" };
		boolean acceptableType = Arrays.stream(acceptedEvents).anyMatch(type::equals);

		if (acceptableType) {
			this.type = type;
		} else {
			throw new IllegalArgumentException("This type is not supported!");
		}
	}

	public void repo(String repo) {
		int idxOfSlash = repo.indexOf("/");

		if (idxOfSlash == -1) {
			throw new IllegalArgumentException("Cannot find '/' in repo String!");
		} else {
			this.repo = repo.substring(idxOfSlash);
		}
	}
}
