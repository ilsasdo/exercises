package exercise

import groovy.transform.CompileStatic

@CompileStatic
class ListUtils {

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
