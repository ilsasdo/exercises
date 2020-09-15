package exercise

import spock.lang.Specification

class ListUtilsSpec extends Specification {

    void "flat out a nested integer array"() {
        expect:
        ListUtils.flatten([1, 2]) == [1, 2]
        ListUtils.flatten([1, [2, 3]]) == [1, 2, 3]
        ListUtils.flatten([1, [2, 3], [8, [9, [[4], 5, 6], 7]]]) == [1, 2, 3, 8, 9, 4, 5, 6, 7]
    }
}
