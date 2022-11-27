package com.bobocode.bobo.generics;

import com.bobocode.bobo.generics.domain.Developer;
import com.bobocode.bobo.generics.domain.Employee;
import com.bobocode.bobo.generics.domain.Team;

import java.util.Comparator;
import java.util.function.Function;

public class App {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1() {
        /**
         * Manually put Developer as a return type of Function
         */
        Function<Team, Developer> teamToDevFunction = Team::getLead;
//        Comparator<Team> comparatorComparingError = createComparatorComparingError(teamToDevFunction);
        /**
         * Implicitly substitutes Employee instead of Developer
         */
        Comparator<Team> comparatorComparing = createComparatorComparingError(Team::getLead);
        /**
         * It is not working because Comparable is implemented in Employee with Employee type,
         * but if we implement Comparable directly in Developer -> it will work
         *
         * but we can add wildcard to make type compatible
         */
        Comparator<Team> comparatorComparingNoError = createComparatorComparingNoError(teamToDevFunction);
        Comparator<Team> comparatorComparingNoError2 = createComparatorComparingNoError2(teamToDevFunction);
    }

    static <T, R extends Comparable<? super R>> Comparator<T> createComparatorComparingNoError(
            Function<T, ? extends R> extractionFunction) {
        return (o1, o2) -> {
            var val1 = extractionFunction.apply(o1);
            var val2 = extractionFunction.apply(o2);
            return val1.compareTo(val2);
        };
    }

    static <T, R extends Comparable<R>> Comparator<T> createComparatorComparingNoError2(
            Function<T, ? extends R> extractionFunction) {
        return (o1, o2) -> {
            var val1 = extractionFunction.apply(o1);
            var val2 = extractionFunction.apply(o2);
            return val1.compareTo(val2);
        };
    }

    static <T, R extends Comparable<R>> Comparator<T> createComparatorComparingError(
            Function<T, R> extractionFunction) {
        return (o1, o2) -> {
            var val1 = extractionFunction.apply(o1);
            var val2 = extractionFunction.apply(o2);
            return val1.compareTo(val2);
        };
    }

    static <T, R extends Comparable<? super R>> Comparator<T> createComparatorComparing(
            Function<? super T, ? extends R> extractionFunction) {
        return (o1, o2) -> {
            var val1 = extractionFunction.apply(o1);
            var val2 = extractionFunction.apply(o2);
            return val1.compareTo(val2);
        };
    }

    public static void test2() {
        Comparator<Employee> employeeComparator = createComparatorComparing(Employee::getEmployeeNumber);
        Function<Developer, Developer.SENIORITY> developerSENIORITYFunction = Developer::getSeniority;

//        Comparator<Developer> developerComparatorError =
//        composeComparatorComparingError(employeeComparator, developerSENIORITYFunction);

        /**
         * T param must match in all arguments
         * If in the return type T is Employee, then in first param in the Function should also be Employee,
         * not Developer
         *
         * but we can specify wildcard to handle situation when T param differs in return type and Function
         */
//        Function<Employee, Developer.SENIORITY> customFunction = e -> getRandomSeniority();
        Comparator<Developer> developerComparatorNoError =
                composeComparatorComparingNoError(employeeComparator, developerSENIORITYFunction);

        Comparator<Developer> developerComparator =
                composeComparatorComparing(employeeComparator, developerSENIORITYFunction);
    }

    static <T, R extends Comparable<? super R>> Comparator<T> composeComparatorComparingNoError(
            Comparator<? super T> comparator,
            Function<T, R> extractionFunction) {
        return (o1, o2) -> {
            var result = comparator.compare(o1, o2);
            var val1 = extractionFunction.apply(o1);
            var val2 = extractionFunction.apply(o2);
            return result != 0 ? result : val1.compareTo(val2);
        };
    }

    static <T, R extends Comparable<? super R>> Comparator<T> composeComparatorComparingError(
            Comparator<T> comparator,
            Function<? super T, ? extends R> extractionFunction) {
        return (o1, o2) -> {
            var result = comparator.compare(o1, o2);
            return result != 0 ? result : extractionFunction.apply(o1).compareTo(extractionFunction.apply(o2));
        };
    }

    static <T, R extends Comparable<? super R>> Comparator<T> composeComparatorComparing(
            Comparator<? super T> comparator,
            Function<? super T, ? extends R> extractionFunction) {
        return (o1, o2) -> {
            var result = comparator.compare(o1, o2);
            return result != 0 ? result : extractionFunction.apply(o1).compareTo(extractionFunction.apply(o2));
        };
    }
}
