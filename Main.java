import java.util.regex.*;

class Main {
	public static void main(String[] args) {
		if (args.length > 0) {
			String username = args[0];
			StringCleaner strCleaner = new StringCleaner();
			if (strCleaner.containsIllegalChar(username)) {
				System.out.println("Username contains illegal characters!");
				throw new IllegalArgumentException(username);
			}
			String url = "https://api.github.com/users/" + args[0] + "/events/public";
			System.out.println(url);
		} else {
			System.out.println("No user provided");
		}
	}
}

class StringCleaner {
	public String illegalChars = "[~#@*+%{}<>\\[\\]|\"\\_^]";

	public boolean containsIllegalChar(String toExamine) {
		Pattern pattern = Pattern.compile(illegalChars);
		Matcher matcher = pattern.matcher(toExamine);
		return matcher.find();
	}
}
