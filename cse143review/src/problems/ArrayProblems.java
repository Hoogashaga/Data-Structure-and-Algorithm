package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    @SuppressWarnings("checkstyle:WhitespaceAround")
    public static String toString(int[] array) {
        // TODO replace this with your code
        if (array.length == 0){
            return "[]";
        }

        int len = array.length;
        String newString = "[";
        for (int index = 0; index < len - 1; index++) {
            newString = newString + array[index] + ", ";
        }
        newString = newString + array[len - 1] + "]";
        return newString;

        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    @SuppressWarnings("checkstyle:WhitespaceAround")
    public static int[] reverse(int[] array) {
        // TODO replace this with your code
        int i = 0;
        int j = array.length - 1;
        int[] reversedArray = new int[j+1];

        for (int index = j; 0 <= index; index--){
            reversedArray[i] = array[index];
            i += 1;
        }

        return reversedArray;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        // TODO replace this with your code
        if(array.length == 0){
            array = array;
        }
        else{
            int j = array.length - 1;
            int tmp = array[j];
            for (int index = j; index > 0; index--){
                array[index] = array[index-1];
            }
            array[0] = tmp;
        }

        // return array;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }
}
