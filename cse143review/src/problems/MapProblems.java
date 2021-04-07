package problems;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    @SuppressWarnings("checkstyle:WhitespaceAfter")
    public static boolean contains3(List<String> list) {
        // TODO replace this with your code
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 0; i < list.size(); i++){
            if(map.containsKey(list.get(i))) {
                int value = map.get(list.get(i)) + 1;
                map.put(list.get(i), value);
                if (map.get(list.get(i)) >= 3) {
                    return true;
                }
            }
            else{
                map.put(list.get(i), 1);
            }
        }
        return false;


        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        // TODO replace this with your code
        Map<String, Integer> m3 = new HashMap<String, Integer>();
        for (String key : m1.keySet()) {
            if (m2.containsKey(key)) {
                if(m1.get(key) == m2.get(key)) {
                    m3.put(key, m2.get(key));
                }
            }
        }
        return m3;

        // throw new UnsupportedOperationException("Not implemented yet.");
    }
}
