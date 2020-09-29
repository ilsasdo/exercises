package exercise

class ListUtils {

    /**
     * Function that will flatten a nested arrays of Integers
     *
     * @param the nested arrays of integers
     * @return the list of all the integers in a "flat" list.
     */
    static List flatten(List list) {
        List acc = []
        List stack = [list]

        while (stack.size() > 0) {
            Object l = stack.remove(0)

            if (l instanceof List) {
                stack.addAll(0, l)
            } else {
                acc << l
            }
        }

        return acc
    }
}
