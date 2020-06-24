package snack.util;



public class StringUtil {

	/**
	 * 判断给定的字符串是否有为空的
	 * @param strs
	 * @return
	 */
	public static boolean checkNull(String... strs) {
		if (strs == null || strs.length <= 0) {
			return true;
		}
		for (String str : strs) {
			if (str == null || "".equals(str)) {
				return true;
			}
		}
		return false;
	}

}
