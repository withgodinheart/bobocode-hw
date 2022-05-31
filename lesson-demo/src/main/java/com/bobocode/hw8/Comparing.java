package com.bobocode.hw8;

import bobocode.data.Accounts;
import bobocode.model.Account;

import java.util.Comparator;
import java.util.function.Function;

/*
Implement  a custom method createComparatorComparing
Declare a generic method that accepts an extraction function and returns a comparator
Specify method type parameters to make sure that extraction function accepts a target type
as input param and returns a comparable type
Inside the method implement a comparing logic based on values that were computed using extraction function
Return comparator instance
 */
public class Comparing {

    public static void main(String[] args) {
        var accountWithMaxBalance = Accounts.generateAccountList(10)
                .stream()
                .max(createComparatorComparing(Account::getBalance))
                .orElseThrow();

        var accountWithMaxBalance2 = Accounts.generateAccountList(10)
                .stream()
                .max(composeComparatorThenComparing(createComparatorComparing(Account::getBalance), Account::getBalance))
                .orElseThrow();

        System.out.println(accountWithMaxBalance);
        System.out.println(accountWithMaxBalance2);
    }

    private static <T, R extends Comparable<? super R>> Comparator<T> createComparatorComparing(
            final Function<? super T, ? extends R> fnc) {
        return (o1, o2) -> fnc.apply(o1).compareTo(fnc.apply(o2));
    }

    private static <T, R extends Comparable<? super R>> Comparator<T> composeComparatorThenComparing(
            final Comparator<? super T> cmp, final Function<? super T, ? extends R> fnc) {
        return (o1, o2) -> {
            var res = cmp.compare(o1, o2);
            return res != 0 ? res : fnc.apply(o1).compareTo(fnc.apply(o2));
        };
    }
}
