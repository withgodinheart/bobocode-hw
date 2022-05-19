package com.bobocode.hw5;

import bobocode.data.Accounts;
import bobocode.model.Account;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * By default it compares only accessible fields, but this can be configured via a constructor property. If no field is
 * available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 */
public class RandomFieldComparator<T> implements Comparator<T> {

    public static void main(String[] args) {
        var cmp = new RandomFieldComparator<>(Account.class, false);
        System.out.println(cmp);
        Accounts.generateAccountList(10).stream().sorted(cmp).forEach(System.out::println);
    }

    private final Field field;

    public RandomFieldComparator(Class<T> targetType) {
        this(targetType, true);
    }

    /**
     * A constructor that accepts a class and a property indicating which fields can be used for comparison. If property
     * value is true, then only public fields or fields with public getters can be used.
     *
     * @param targetType                  a type of objects that may be compared
     * @param compareOnlyAccessibleFields config property indicating if only publicly accessible fields can be used
     */
    public RandomFieldComparator(Class<T> targetType, boolean compareOnlyAccessibleFields) {
        final Field[] fields = compareOnlyAccessibleFields ? targetType.getFields() : targetType.getDeclaredFields();
        this.field = findRandomField(fields);
    }

    private Field findRandomField(final Field[] fields) {
        if (fields.length == 0) {
            throw new IllegalArgumentException("There are no available fields");
        }

        int index = ThreadLocalRandom.current().nextInt(fields.length);
        var random = fields[index];
        random.setAccessible(true);

        return random;
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value grater than a non-null value (nulls last).
     */
    @Override
    @SneakyThrows
    @SuppressWarnings({"rawtypes", "unchecked"})
    public int compare(T o1, T o2) {
        var c1 = (Comparable) field.get(o1);
        var c2 = (Comparable) field.get(o2);

        if (c1 == null && c2 == null) {
            return 0;
        } else if (c1 == null) {
            return 1;
        } else if (c2 == null) {
            return -1;
        } else {
            return c1.compareTo(c2);
        }
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class '%s' is comparing '%s'", this.field.getGenericType(), this.field.getName());
    }
}
