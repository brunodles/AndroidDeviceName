/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brunodles.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bruno
 */
public class JsonHelper {

    private static final String JSON_REGEX = "\\{(.*?)\\}";
    private static final String JSON_FIELD_REGEX = "[\\{\\\"]*(.*?)[\\\"]?\\:[\\\"]?(.*?)[\\\"]?[\\,\\}\\\r\\\n]";

    private static final Pattern JSON_PATTERN = Pattern.compile(JSON_REGEX);
    private static final Pattern JSON_FIELD_PATTERN = Pattern.compile(JSON_FIELD_REGEX);

    public static String findFirstJson(String json) {
        Matcher matcher = JSON_PATTERN.matcher(json);
        if (matcher.find())
            return matcher.group(0);
        return null;
    }

    public static List<String> findJsonList(String json) {
        ArrayList<String> list = new ArrayList<String>();

        Matcher matcher = JSON_PATTERN.matcher(json);
        while (matcher.find())
            list.add(matcher.group(0));

        return list;
    }

    public static String findListBracers(String json) {
        int first = json.indexOf("[");
        int last = json.lastIndexOf("]");
        return json.substring(first + 1, last);
    }

    public static String toJson(Map<String, String> values) {
        StringBuilder b = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (first)
                first = false;
            else
                b.append(",");
            b.append("\"")
                    .append(entry.getKey())
                    .append("\":\"")
                    .append(entry.getValue())
                    .append("\"");
        }
        return b.append("}").toString();
    }

    public static HashMap<String, String> toValues(String json) {
        HashMap<String, String> map = new HashMap<String, String>();
        Matcher m = JSON_FIELD_PATTERN.matcher(json);
        while (m.find()) {
            map.put(m.group(1), m.group(2));
//            System.out.printf("%s : %s\n", m.group(1), m.group(2));
        }
        return map;
    }
}
