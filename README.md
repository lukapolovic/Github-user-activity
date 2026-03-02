# GitHub User Activity (Java CLI)

Small command-line app that fetches a GitHub user's recent public events and prints activity grouped by repository and event type.

Project idea source: [roadmap.sh - GitHub User Activity](https://roadmap.sh/projects/github-user-activity)

## Requirements

- Java 11+ (for `java.net.http.HttpClient`)
- Internet connection (calls GitHub public API)

## Quick Start

Compile:

```bash
javac Main.java HttpHandler.java RepoActivity.java UserEvent.java EventType.java
```

Run:

```bash
java Main <github-username>
```

Example:

```bash
java Main torvalds
```

## Sample Output

```text
In linux repository:
 - The user had 22 PushEvent

In AudioNoise repository:
 - The user had 1 PushEvent
 - The user had 2 IssueCommentEvent

In uemacs repository:
 - The user had 1 CreateEvent
 - The user had 3 PushEvent
 - The user had 1 IssueCommentEvent
```

## Notes

- Username must contain only letters, numbers, or `-`.
- Output depends on the user's latest public activity from GitHub.