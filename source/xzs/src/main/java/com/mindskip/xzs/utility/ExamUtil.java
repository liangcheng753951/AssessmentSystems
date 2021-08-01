package com.mindskip.xzs.utility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExamUtil {

    public static String scoreToVM(Integer score) {
        if (score % 10 == 0) {
            return String.valueOf(score / 10);
        } else {
            return String.format("%.1f", score / 10.0);
        }
    }

    public static Integer scoreFromVM(String score) {
        if (score == null) {
            return null;
        } else {
            return (int) (Float.parseFloat(score) * 10);
        }
    }

    public static String secondToVM(Integer second) {
        String dateTimes;
        long days = second / (60 * 60 * 24);
        long hours = (second % (60 * 60 * 24)) / (60 * 60);
        long minutes = (second % (60 * 60)) / 60;
        long seconds = second % 60;
        if (days > 0) {
            dateTimes = days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        } else if (hours > 0) {
            dateTimes = hours + "h " + minutes + "m " + seconds + "s";
        } else if (minutes > 0) {
            dateTimes = minutes + "m " + seconds + "s";
        } else {
            dateTimes = seconds + " s";
        }
        return dateTimes;
    }

}
