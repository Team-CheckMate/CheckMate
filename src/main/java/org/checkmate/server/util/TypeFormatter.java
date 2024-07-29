package org.checkmate.server.util;

/**
 * Boolean, Integer 타입 포맷팅 클래스
 * HISTORY1: 최초 생성                                         [송헌욱  2024.07.24]
 * HISTORY2: Boolean,Integer to String 메서드 추가             [이준희  2024.07.28]
 */
public class TypeFormatter {

    /**
     * Boolean -> Integer
     * @param value 변환할 Boolean 값
     * @return Boolean 값이 'true' 인 경우 1, 'false' 인 경우 0, 'null' 인 경우 null .
     */
    public static Integer BooleanToInteger(Boolean value) {
        if (value == null) {
            return null;
        }
        return value ? 1 : 0;
    }

    /**
     * Integer -> Boolean
     * @param value 변환할 Integer 값
     * @return Integer 값이 '1' 인 경우 true, '0' 인 경우 false, 'null' 인 경우 null.
     */
    public static Boolean IntegerToBoolean(Integer value) {
        if (value == null) {
            return null; // Optionally handle null case separately if needed
        }
        return value == 1;
    }

    /**
     * Boolean -> String
     * @param value 변환할 Boolean 값
     * @return Boolean 값이 true 인 경우 대여중, false 인 경우 대여가능, 'null' 인 경우 null.
     */
    public static String BooleanToString(Boolean value) {
        if (value == null) {
            return null; // Optionally handle null case separately if needed
        }
        return value ? "대여중" : "대여가능" ;
    }

    /**
     * Integer -> String
     * @param value 변환할 Integer 값
     * @return Integer 값이 1 인 경우 대여중, 0 인 경우 대여가능, 'null' 인 경우 null.
     */
    public static String IntegerToString(Integer value) {
        if (value == null) {
            return null; // Optionally handle null case separately if needed
        }
        return value == 1? "대여중": "대여가능" ;
    }

}
