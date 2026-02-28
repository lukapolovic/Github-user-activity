import java.util.Arrays;

class UserEvent {
	private long id;
	private String type;
	private String repo;

	public UserEvent(long id, String type, String repo) {
		setId(id);
		setType(type);
		setRepo(repo);
	}

	public long getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}

	public String getRepo() {
		return this.repo;
	}

	public void setId(long id) {
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

	public void setRepo(String repo) {
		int idxOfSlash = repo.indexOf("/");

		if (idxOfSlash == -1) {
			throw new IllegalArgumentException("Cannot find '/' in repo String!");
		} else {
			this.repo = repo.substring(idxOfSlash + 1);
		}
	}
}
