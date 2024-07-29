package org.checkmate.server.util;

import java.util.regex.Pattern;

/**
 * 문자열 나누는 Util 클래스 생성
 * HISTORY1: 최초 생성                              [이준희  2024.07.27]
 */

public class StringSplit {
    public static String splitAndGetFirst(String input, String delimiter) {
        if (input == null || delimiter == null) {
            throw new IllegalArgumentException("Input and delimiter must not be null");
        }
        // Pattern.quote -> 특수기호는 \\붙여야하는데 이걸로 방지가능
       String inputs[] =  input.split(Pattern.quote(delimiter));
        return inputs[0];
    }

    public static int getCategoryNum(String input, String delimiter){
        return Integer.parseInt(splitAndGetFirst(input,delimiter));
    }
}
