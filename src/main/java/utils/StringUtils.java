package utils;

import com.google.common.base.Strings;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class StringUtils {
    private static StringUtils instance;
    private StringUtils() {}

    public static StringUtils getInstance() {
        if (instance == null) {
            instance = new StringUtils();
        }
        return instance;
    }

    public List<String> subStrings(String str, int len) {
        List<String> strings = new ArrayList<>();
        int p = 0;
        while (p < str.length()) {
            String toAdd = str.substring(p, len);
            if (Strings.isNullOrEmpty(toAdd)) {
                toAdd = str.substring(p);
            }
            strings.add(toAdd);
            p += len;
        }
        return strings;
    }
}
