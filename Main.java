import java.net.http.HttpResponse;
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
			HttpResponse<String> response = new HttpHandler().getResponse(url);
			System.out.println("Status code: " + response.statusCode());
			System.out.println("Response body: " + response.body());
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
}
