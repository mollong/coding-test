package filesort;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Solution {
    public String[] solution(String[] files) {
        List<File> fileList = new ArrayList<>();

        for (String file : files) {
            fileList.add(new File(file));
        }
        fileList.sort(new FileComparator());

        return fileList.stream()
                .map(File::toString)
                .collect(Collectors.toList())
                .toArray(new String[fileList.size()]);
    }
}

class File {
    private final String name;
    private final String head;
    private final Integer number;

    File(String name) {
        this.name = name;
        Pattern pattern = Pattern.compile("^([\\D\\-\\s]+)([\\d]+)");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.find()) throw new RuntimeException("no matches Log matcher's pattern");
        head = matcher.group(1).toLowerCase();
        number = Integer.parseInt(matcher.group(2));
    }

    public String getHead() {
        return head;
    }

    public Integer getNumber() {
        return number;
    }

    public String toString() {
        return name;
    }
}

class FileComparator implements Comparator<File> {
    @Override
    public int compare(File f1, File f2) {
        int compare = f1.getHead().compareTo(f2.getHead());
        return (compare == 0) ? f1.getNumber().compareTo(f2.getNumber()) : compare;
     }
}

public class FileSort {
    public static void main(String[] args) {
        String[] files1 = new String[]{
            "img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"
        };
        String[] files2 = new String[]{
            "F-5 Freedom Fighter",
            "B-50 Superfortress",
            "A-10 Thunderbolt II",
            "F-14 Tomcat"
        };

        String[] answer = new Solution().solution(files1);
        for (String name : answer) {
            System.out.println(name);
        }

        System.out.println();

        answer = new Solution().solution(files2);
        for (String name : answer) {
            System.out.println(name);
        }
    }
}
