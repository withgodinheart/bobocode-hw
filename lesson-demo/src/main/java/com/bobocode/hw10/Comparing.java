package com.bobocode.hw10;

import java.util.Comparator;
import java.util.function.Function;

public class Comparing {

    public static void main(String[] args) {

    }

    /*
     Implement  a custom method createComparatorComparing
Declare a generic method that accepts an extraction function and returns a comparator
Specify method type parameters to make sure that extraction function accepts a target type as input param and returns a comparable type
Inside the method implement a comparing logic based on values that were computed using extraction function
Return comparator instance
     */
    private static <T, R extends Comparable<? super R>> Comparator<T>
    createComparatorComparing(final Function<? super T, ? extends R> fnc) {
        return (o1, o2) -> fnc.apply(o1).compareTo(fnc.apply(o2));
    }

    /*
    Implement  a custom method composeComparatorThenComparing
A method should accept two parameters:
Comparator
Extraction function
And return a new comparator instance that is using a given comparator, and in case comparing values are equals it compares values that are computed based on the extraction function.
     */
    private static <T, R extends Comparable<? super R>> Comparator<T>
    composeComparatorThenComparing(final Comparator<? super T> cmp, final Function<? super T, ? extends R> fnc) {
        return (o1, o2) -> {
            var res = cmp.compare(o1, o2);
            return res != 0 ? res : fnc.apply(o1).compareTo(fnc.apply(o2));
        };
    }
}
