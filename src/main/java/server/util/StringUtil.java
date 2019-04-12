package server.util;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
    /**
     * @param str
     * @return
     */
    public static String replaceNull(String str) {
        if (str == null || str.equals("") || str.equals("null")) {
            str = "";
        }
        return str;
    }

    /**
     * 将Blob类型的值转为字符串
     * 
     * @param cont
     * @return
     */
    public static String readBlobAsString(Blob cont) {
        if (cont == null) {
            return null;
        }

        try {
            return new String(cont.getBytes(1L, (int)cont.length()));
        }
        catch (SQLException e) {
            return null;
        }
    }

    /**
     * @author：wangyy
     * @date：2015-7-10
     * @Description：遍历传进来的数，并以传入的分割符进行分割
     * @return void: 返回值类型
     * @throws
     *//*
    public static String traversalNum(Integer num, String splitStr) {
        if (num == null) {
            return null;
        }

        if (StringUtils.isEmpty(splitStr)) {
            splitStr = ",";
        }
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < num; i++) {
            str.append(i);
            if (i != num - 1) {
                str.append(splitStr);
            }
        }
        return str.toString();
    }*/

    /**
     * 根据字节长度截取字串
     * 
     * @param input
     * @param n
     * @return
     */
    public static String substrb(String input, int n) {
        if (n <= 0) {
            return "";
        }

        byte[] bytes = input.getBytes();
        if (n >= bytes.length) {
            return input;
        }

        byte[] data = new byte[n]; // 结果

        int x = 0;
        int y = 0;
        while (x < n) {
            int length = Character.toString(input.charAt(y)).getBytes().length;
            if (length > 1) { // 双字节
                if (x >= n - 1) { // 如果是最后一个字节
                    break;
                }

                data[x] = bytes[x];
                data[x + 1] = bytes[x + 1];
                x += 2;
                y++;
            }
            else {
                data[x] = bytes[x];
                x++;
                y++;
            }
        }
        return new String(data, 0, x);
    }

    /**
     * 得到字符串的半角长度
     * 
     * @param strInput
     *            待检测的字符串
     * @return 字符串的长度
     */

    public static int length(String strInput) {
        if (!isNull(strInput)) {
            int i = 0;
            Pattern p = Pattern.compile("[\u4E00-\u9FA5]");
            Matcher m = p.matcher(strInput);
            while (m.find())
                i++;
            return strInput.length() + i / 2;
        }
        return 0;
    }

    /**
     * 得到字符串是否为空
     * 
     * @param strInput
     *            待检测的字符串
     * @return 若为空返回 true
     */
    @Deprecated
    public static boolean isNull(String strInput, String... strings) {
        if (strInput != null && !strInput.trim().equals("")) {
            return false;
        }
        for (String s : strings) {
            if (!isNull(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 得到长度为ilength的格式化字符串
     * 
     * @param strInput
     *            待检测的字符串
     * @param ilength
     *            目标长度
     * @param isLeft
     *            为true时从左侧加入空格,否则从右加入
     * @return 格式化后的字符串
     */

    public static String formatToLength(String strInput, int ilength, boolean isLeft) {
        if (!isNull(strInput)) {
            if (length(strInput) < ilength) {
                if (isLeft) {
                    String temp = " ";
                    for (int i = length(strInput); i < ilength; i++) {
                        temp = temp.concat(" ");
                    }
                    strInput = temp.concat(strInput);
                }
                else {
                    for (int i = length(strInput); i < ilength; i++) {
                        strInput = strInput.concat(" ");
                    }
                }
            }
            return strInput;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ilength; i++)
            sb.append(" ");
        return sb.toString();
    }

    /**
     * 将List<HashMap>中的值，转换为以分隔符分割的字符串
     * 
     * @param list
     *            待转换的List
     * @param split
     *            分割符
     * @param preFix
     *            添加的前缀
     * @param afterFix
     *            添加的后缀
     * @return 转换后的字符串
     */
    public static String conditionConvert(List list, String split, String preFix, String afterFix) {
        StringBuilder builder = new StringBuilder();
        int lsLen = list.size();
        for (int index = 0; index < lsLen; index++) {
            if (index > 0) {
                builder.append(split);
            }
            builder.append(preFix);
            builder.append(list.get(index).toString());
            builder.append(afterFix);
        }

        return builder.toString();
    }

    /**
     * 将List中的值，转换为以逗号分隔的字符串，每个值，以"'"分割
     * 
     * @param list
     *            待转换的List<HashMap>
     * @return 转换后的字符串
     */
    @Deprecated
    public static String ConditionConvertStr(List list) {
        return conditionConvert(list, ",", "'", "'");
    }

    public static String convertWithCode(String str, String orgiCode, String newCode) {
        try {
            if (isNull(str) || isNull(newCode)) {
                return str;
            }

            byte[] bytes = str.getBytes(isNull(orgiCode) ? "ISO8859-1" : orgiCode);

            return new String(bytes, newCode);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }

    }

    /**
     * 把String中的特殊字符串(",",<,>,&)转换为转义字符
     * 
     * @param str
     *            待转换的String
     * @return 转换后的字符串
     */
    public static String convertSpecChar(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\'':
                sb.append("&apos;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 获取非""值的字符串,如果字符串的值为"",则返回null
     * 
     * @param value
     * @return
     */
    public static String getNotEmptyValue(String value) {
        return isNull(value) ? null : value;
    }

    public static String nvl(Object str) {
        return nvl(str, "");
    }

    public static String nvl(Object str, String out) {
        if (str != null && !str.equals("")) {
            return "" + str;
        }
        return out;

    }

    public static boolean isNull(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static boolean isNull(Object obj) {
        return obj == null || isNull(obj.toString());
    }

    public static boolean containStrIgnorCase(String source, String match) {
        Pattern pattern = Pattern.compile(".?" + match + ".?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }

    public static boolean containStrIgnorCase2(String source, String match) {
        if (source == match || source == null && match == null) {
            return true;
        }
        else if ((source == null && match != null) || (source != null && match == null)) {
            return false;
        }
        else {
            return source.toLowerCase().indexOf(match.toLowerCase()) > -1;
        }
    }

    /**
     * 查看字符串source里面有没有包含match这个字符串，有就返回true，没有就返回false
     * 
     * @param source
     * @param match
     * @return
     */
    public static boolean containStr(String source, String match) {
        if (source == match || (source == null && match == null) || (source != null && (match == null || match.trim().length() == 0))) {
            return true;
        }
        else if ((source == null && match != null) || (source != null && match == null)) {
            return false;
        }
        else {
            return source.indexOf(match) > -1;
        }
    }

    public static boolean isNumeric(String str) {
        final String number = "0123456789.";
        for (int i = 0; i < str.length(); i++) {
            if (number.indexOf(str.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    public static String formatJsonStr(String jsonStr) {
        if (isNull(jsonStr)) return null;

        return jsonStr.replace("\"", "\\\"");
    }

    public static String toString(Object value) {
        return value == null ? "" : value.toString();
    }

    public static String toString(Object value, String defaltVal) {
        return value == null ? defaltVal : value.toString();
    }

    public static boolean equals(Object obj1, Object obj2, boolean ignorNull) {
        if (!ignorNull) {
            return equals(obj1, obj2);
        }
        else {
            if (obj1 == null && obj2 == null) {
                return false;
            }
            else {
                return equals(obj1, obj2);
            }
        }
    }

    public static boolean equals(Object obj1, Object obj2) {
        return equalsIgnorCase(obj1, obj2);
    }

    public static boolean equalsStrIgnorCase(String value1, String value2) {
        if (value1 == value2 || value1 == null && value2 == null) {
            return true;
        }
        else if ((value1 == null && value2 != null) || (value1 != null && value2 == null)) {
            return false;
        }
        else {
            return value1.equalsIgnoreCase(value2);
        }
    }

    /**
     * @param value1
     * @param value2
     * @param ignorNull
     *            false: 如果两个都为Null，将相等,反则不相
     * @return
     */
    public static boolean equalsStrIgnorCase(String value1, String value2, boolean ignorNull) {
        if (!ignorNull) {
            return equalsStrIgnorCase(value1, value2);
        }
        else {
            if (value1 == null || value2 == null) {
                return false;
            }
            else {
                return equalsStrIgnorCase(value1, value2);
            }
        }
    }

    public static boolean equalsIgnorCase(Object value1, Object value2) {
        if (value1 == value2 || value1 == null && value2 == null) {
            return true;
        }
        else if ((value1 == null && value1 != null) || (value2 != null && value2 == null)) {
            return false;
        }
        else {
            return equalsStrIgnorCase(toString(value1), toString(value2));
        }
    }

    /**
     * 对字段串按照指定的字符进行截取
     * 
     * @param value
     *            字段串
     * @param chara
     *            以此字段串为开始进行截取
     * @param charaIndex
     *            chara在字段串中的位置，-1表示最后，1表示最开始
     * @return
     */
    public static String subStr(String value, String chara, int charaIndex) {
        if (isNull(value) || isNull(chara)) {
            return null;
        }
        else {
            int startIndex = 0;
            int endIndex = value.length();
            if (-1 == charaIndex) {
                startIndex = value.lastIndexOf(chara) + 1;
            }
            else {
                String[] charaSplited = value.split(chara);

                if (charaIndex <= charaSplited.length - 1) {
                    int remainLenth = 0;
                    for (int i = charaSplited.length - 1; i > charaIndex - 1; i--) {
                        remainLenth += (charaSplited[i].length() + chara.length());
                    }
                    startIndex = endIndex - (remainLenth - chara.length());
                }
            }
            return value.substring(startIndex, endIndex);
        }
    }

    /**
     * @param value
     *            同substr一样
     * @param chara
     *            同substr一样
     * @param charaIndex
     *            同substr一样
     * @param frant
     *            true: 截取前面，false：截取后面
     * @return
     */
    public static String subStr(String value, String chara, int charaIndex, boolean frant) {

        if (!frant) {
            return subStr(value, chara, charaIndex);
        }

        if (isNull(value) || isNull(chara)) {
            return null;
        }
        else {
            int startIndex = 0;
            int endIndex = value.length();
            if (-1 == charaIndex) {
                startIndex = value.lastIndexOf(chara) + 1;
            }
            else {
                String[] charaSplited = value.split(chara);

                if (charaIndex <= charaSplited.length - 1) {
                    int remainLenth = 0;
                    for (int i = charaSplited.length - 1; i > charaIndex - 1; i--) {
                        remainLenth += (charaSplited[i].length() + chara.length());
                    }
                    startIndex = endIndex - (remainLenth - chara.length());
                }
            }
            return value.substring(0, startIndex - 1);
        }
    }

    /**
     * 字符串转换成Unicode编码，适用于一个字符占两个字节的字符串
     * 
     * @param str
     *            要转换的字符串
     * @return
     */
    public static String toUnicode(String str) {
        if (isNull(str)) {
            return null;
        }
        StringBuilder unicode = new StringBuilder();
        char[] charAry = str.toCharArray();
        for (int i = 0; i < charAry.length; i++) {
            // 如果包含中文
            if (new Character(charAry[i]).toString().matches("[\\u4e00-\\u9fbf]")) {
                unicode.append("\\u" + Integer.toString(charAry[i], 16));
            }
            else {
                unicode.append(charAry[i]);
            }
        }
        return unicode.toString();
    }

    /**
     * 根据长度截取字符串
     * 
     * @param str
     *            字符串
     * @param length
     *            要截取的长度
     * @param replace
     *            超过长度的需要替代的字符串
     * @return
     */
    public static String getStr(String str, int length, String replace) {
        if (str.length() > length) {
            str = str.substring(0, length);
            str += replace;
        }
        return str;
    }

    /**
     * 根据长度截取字符串，超过长度的字符中将用空格替代
     * 
     * @param str
     * @param length
     * @return
     */
    public static String getStr(String str, int length) {
        return getStr(str, length, "");
    }

    public static String convertErrStack(StackTraceElement[] trace) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < trace.length; i++) {
            str.append(String.valueOf(trace[i]) + "\n");
        }
        return str.toString();
    }

    public static boolean isEmpty(String str) {
        if (isNull(str)) {
            return true;
        }
        if (str.trim().equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * @author：wangyy
     * @date：2014-11-21
     * @Description：将map转换成字符串
     * @return void: 返回值类型
     * @param separate
     *            (:):KEY与value之间的分隔符：例如：name：王五;joinSeparate(;)：多个值之间的分隔：例如name:张三;name:李四
     * @throws
     */
    public static String parseMapToString(Map<String, String> map, String separate, String joinSeparate) {
        if (map == null || map.size() == 0) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        int index = 0;
        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            str.append(key + separate + map.get(key));
            if (index != map.size() - 1) {
                str.append(joinSeparate);
            }
            index++;
        }
        return str.toString();
    }

   /* *//**
     * 解析字符串为list, 会去掉首位空格
     *
     * @author Kee.Li
     * @create 2015-5-30
     * @param str
     *            目标字符串
     * @param split
     *            分隔符，为空默认是逗号,
     * @return
     *//*
    public static List<String> parseStringToList(String str, String split) {

        if (StringUtils.isEmpty(str)) {
            return new ArrayList<String>();
        }

        if (StringUtils.isEmpty(split)) {
            split = ",";
        }

        List<String> list = new ArrayList<String>();
        if (str.indexOf(split) == -1) {
            list.add(str.trim());
        }
        else {
            String[] strs = str.split(split);
            for (String string : strs) {
                if (StringUtils.isNotBlank(string)) {
                    list.add(string.trim());
                }
            }
        }
        return list;
    }*/

    public static Object sqlEscaped(Object obj){
    	if (obj instanceof String) {
    		String value = (String)obj;
	    	
	    	if (value.contains("\\")) {
	        	value = value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\\\", "\\\\\\\\");
	        }
	        if (value.contains("/")) {
	        	value = value.replaceAll("/", "\\/");
	        }
	        if (value.indexOf("'") != -1) {
	        	value =  value.replaceAll("'", "''");
	        }
	    	if (value.indexOf("_") != -1) {
	    		value =  value.replaceAll("_", "\\\\_");
	        }
	        if (value.indexOf("%") != -1) {
	        	value =  value.replaceAll("%", "\\\\%");
	        }
	        if (value.indexOf("?") != -1) {
	        	value =  value.replaceAll("\\?", "\\\\?");
	        }
	        return value;
    	}
    	return obj;
    }
}
