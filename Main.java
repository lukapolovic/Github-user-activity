import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.*;

class Main {
	public static void main(String[] args) {
		if (args.length > 0) {
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

			for (String str : cleanedValues) {
				System.out.println(str);
			}
		} else {
			System.out.println("No user provided");
		}
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
}
