import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.*;

class Main {
	public static void main(String[] args) {
		if (args.length <= 0) {
			System.out.println("No user provided");
		}

		String username = args[0];
		StringCleaner strCleaner = new StringCleaner();
		if (!strCleaner.isValidString(username)) {
			System.out.println("Username contains illegal characters!");
			throw new IllegalArgumentException(username);
		}

		String url = "https://api.github.com/users/" + args[0] + "/events/public";
		String response = new HttpHandler().getResponse(url);
		String cleanedResponse = response.substring(1, response.length() - 1);

		List<String> extValues = strCleaner.extractInfo(cleanedResponse);
		List<String> cleanedValues = strCleaner.cleanFieldValues(extValues);

		List<UserEvent> userEvents = Main.returnUserEvents(cleanedValues);

		Set<String> distinctRepos = Main.getDistinctRepos(userEvents);

		Map<String, RepoActivity> repoActivities = new HashMap<>();
		for (String repo : distinctRepos) {
			repoActivities.put(repo, new RepoActivity(repo));
		}

		for (UserEvent event : userEvents) {
			String repoName = event.getRepo();
			RepoActivity repoActivity = repoActivities.get(repoName);
			repoActivity.addEvent(event.getType());
		}

		repoActivities.values().forEach(repo -> repo.getEventsByEventType());

		repoActivities.values().forEach(repo -> System.out.println(repo.toString()));
	}

	public static List<UserEvent> returnUserEvents(List<String> array) {
		StringCleaner strCleaner = new StringCleaner();
		List<UserEvent> userEvents = new ArrayList<>();

		for (int i = 0; i < array.size(); i += 3) {
			long id = Long.parseLong(strCleaner.getFieldValue(array.get(i)));
			String type = strCleaner.getFieldValue(array.get(i + 1));
			String repo = strCleaner.getFieldValue(array.get(i + 2));

			UserEvent usrEvent = new UserEvent(id, type, repo);
			userEvents.add(usrEvent);
		}

		return userEvents;
	}

	public static Set<String> getDistinctRepos(List<UserEvent> array) {
		Set<String> repos = new HashSet<>();

		for (UserEvent event : array) {
			repos.add(event.getRepo());
		}

		return repos;
	}
}

class StringCleaner {
	public String regex = "^[-a-zA-Z0-9]*$";

	public boolean isValidString(String toExamine) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(toExamine);
		return matcher.matches();
	}

	public List<String> extractInfo(String body) {
		String[] splitBody = body.split("},");
		List<String> extractedField = new ArrayList<>();

		for (String str : splitBody) {
			String firstKeyField = str.substring(0, 6);
			String idKeyField = "{\"id\":";
			String repoKeyField = "\"repo\"";
			String[] splitField = str.split(",");

			if (firstKeyField.equals(idKeyField)) {
				extractedField.add(splitField[0]);
				extractedField.add(splitField[1]);
			} else if (firstKeyField.equals(repoKeyField)) {
				extractedField.add(splitField[1]);
			} else {
				continue;
			}
		}

		return extractedField;
	}

	public List<String> cleanFieldValues(List<String> array) {
		List<String> cleanedList = new ArrayList<>();

		for (String str : array) {
			String cleanedStr = str.replaceAll("[{\"}]", "");
			cleanedList.add(cleanedStr);
		}

		return cleanedList;
	}

	public String getFieldValue(String str) {
		return str.split(":")[1];
	}
}
