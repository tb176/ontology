package util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具
 * 
 * @author tengbei
 * 
 */
public class DateUtils {

	/**
	 * 校验字符串是否是日期格式
	 * 
	 * @param dateString
	 * @return 如果是日期格式字符串 则返回true 否则返回 false
	 * @throws Exception
	 */
	public static boolean isDateString(String dateString) throws Exception {
		if (Utils.isNull(dateString))
			return false;
		try {
			if (dateString.trim().length() > 11) { // 如果含有时分秒
				string2Date(dateString.trim(), "yyyy-MM-dd HH:mm:ss");
				return true;
			} else {
				string2Date(dateString.trim(), "yyyy-MM-dd");
				return true;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 校验日期是否是有效的
	 * 
	 * @param dateString
	 * @return
	 */
	public static boolean isValidDate(String dateString) throws Exception {
		if (Utils.isNull(dateString))
			return false; // 如果是空则返回false
		if (!isDateString(dateString))
			return false; // 如果不符合日期格式也返回false
		try {
			String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?	((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
			if (dateString.trim().length() > 11) {// 含有时分秒的
				regex += "(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
			}
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(dateString);
			return match.matches();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 根据给定的格式进行把日期字符串转换成日期
	 * 
	 * @param dateString
	 *            日期字符串 如果日期为空，则显示当前日期
	 * @param formatString
	 *            格式化的格式：yyyy-MM-dd、yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 */
	public static Date string2Date(String dateString, String formatString)
			throws Exception {
		if (Utils.isNull(formatString))
			formatString = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		if (Utils.isNull(dateString))// 如果是空
			return format.parse(new Date().toString());
		return format.parse(dateString);
	}

	/**
	 * 根据给定的格式显示日期
	 * 
	 * @param date
	 *            日期对象 如果日期为空，则显示当前日期
	 * @param formatString
	 *            预格式化的格式：yyyy-MM-dd、yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 */
	public static String date2String(Date date, String formatString)
			throws Exception {
		if (Utils.isNull(formatString))
			formatString = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		if (date == null)
			return format.format(new Date());
		return format.format(date);
	}


	/**
	 * 转换成大写日期
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String toChineseDate(String date) throws Exception {
		return num2ChineseStr(date);
	}

	/**
	 * 转换成大写字母
	 * 
	 * @param str
	 * @throws Exception
	 */
	private static String num2ChineseStr(String str) throws Exception {
		if (Utils.isNull(str))
			return "";
		Map<Character, String> map = new HashMap<Character, String>();
		map.put('0', "〇");
		map.put('1', "一");
		map.put('2', "二");
		map.put('3', "三");
		map.put('4', "四");
		map.put('5', "五");
		map.put('6', "六");
		map.put('7', "七");
		map.put('8', "八");
		map.put('9', "九");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if (Utils.isNotNull(map.get(str.charAt(i)))) {
				sb.append(map.get(str.charAt(i)));
			} else {
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}
}
