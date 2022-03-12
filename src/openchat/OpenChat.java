package openchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {
    private final Map<String, String> users = new HashMap<>();
    private final List<Log> logs = new ArrayList<>();

    public String[] solution(String[] record) {
        for (String rec : record) save(rec);

        return logs.stream()
            .filter(log -> log.getCommand() != Command.CHANGE)
            .map(log -> log.toMessage(users.get(log.getUserId())))
            .toArray(String[]::new);
    }

    private void save(String record) {
        Log log = new Log(record);
        if (log.getCommand() == Command.ENTER || log.getCommand() == Command.CHANGE) {
            users.put(log.getUserId(), log.getNickname());
        }
        logs.add(log);
    }
}

enum Command {
    ENTER, // 입장
    LEAVE, // 퇴장
    CHANGE // 변경
}

class Log {
    private static final Pattern pattern = Pattern.compile("^([\\w]+)\\s([\\w]+)\\s?([\\w]*)$");

    private Command command;
    private final String userId;
    private final String nickname;

    Log(String record) {
        Matcher matcher = pattern.matcher(record);
        if (!matcher.find()) throw new RuntimeException("no matches Log matcher's pattern");
        setCommand(matcher.group(1));
        userId = matcher.group(2);
        nickname = matcher.group(3);
    }

    public Command getCommand() {
        return command;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    private void setCommand(String command) {
        switch (command.toLowerCase()) {
            case "enter":
                this.command = Command.ENTER;
                break;
            case "leave":
                this.command = Command.LEAVE;
                break;
            case "change":
                this.command = Command.CHANGE;
                break;
        }
    }

    public String toMessage(String nickname) {
        String message = null;

        switch (command) {
            case ENTER:
                message = String.format("%s님이 들어왔습니다.", nickname);
                break;
            case LEAVE:
                message = String.format("%s님이 나갔습니다.", nickname);
                break;
        }

        return message;
    }
}

public class OpenChat {
    public static void main(String[] args) {
        String[] record = new String[]{
            "Enter uid1234 Muzi",
            "Enter uid4567 Prodo",
            "Leave uid1234",
            "Enter uid1234 Prodo",
            "Change uid4567 Ryan"
        };

        String[] answer = new Solution().solution(record);

        for (String ans : answer) {
            System.out.println(ans);
        }
    }
}
