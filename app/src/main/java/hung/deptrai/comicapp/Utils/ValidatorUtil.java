package hung.deptrai.comicapp.Utils;

public class ValidatorUtil {
    public static boolean emptyValue(String str) {
        if (null == str || str.equals("")) {
            return true;
        }
        return false;
    }
}