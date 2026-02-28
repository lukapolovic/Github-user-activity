import java.util.Arrays;

class UserEvent {
	private long id;
	private EventType type;
	private String repo;

	public UserEvent(long id, String type, String repo) {
		setId(id);
		setType(type);
		setRepo(repo);
	}

	public long getId() {
		return this.id;
	}

	public EventType getType() {
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
		try {
			this.type = EventType.valueOf(type);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("This type is not supported: " + type);
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

	public enum EventType {
		CommmitCommentEvent,
		CreateEvent,
		DeleteEvent,
		DiscussionEvent,
		ForkEvent,
		GollumEvent,
		IssueCommentEvent,
		IssuesEvent,
		MemberEvent,
		PublicEvent,
		PullRequestEvent,
		PullRequestReviewEvent,
		PullRequestReviewCommentEvent,
		PushEvent,
		ReleaseEvent,
		WatchEvent
	}
}
