package exercise

import groovy.transform.CompileStatic

@CompileStatic
class ListUtils {

    /**
     * Function that will flatten a nested arrays of Integers
     *
     * @param the nested arrays of integers
     * @return the list of all the integers in a "flat" list.
     */
    static List<Integer> flatten(List integers) {
        List<Integer> flattened = []

        for (Object entry : integers) {
            if (entry instanceof Integer) {
                flattened.add(entry)
            } else if (entry instanceof List) {
                flattened.addAll(flatten(entry))
            }
        }

        return flattened
    }
}
