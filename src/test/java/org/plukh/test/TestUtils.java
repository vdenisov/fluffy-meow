package org.plukh.test;

import com.google.common.collect.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.plukh.fluffymeow.Equatable;

import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private static class EquatableWrapper<T extends Equatable> {
        public T equatable;

        public EquatableWrapper(T equatable) {
            this.equatable = equatable;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EquatableWrapper)) return false;

            EquatableWrapper<?> that = (EquatableWrapper<?>) o;

            return equatable.isEqual(that.equatable);
        }

        @Override
        public int hashCode() {
            return equatable.hashCode();
        }

        @Override
        public String toString() {
            return equatable.toString();
        }
    }

    public interface Comparator<T> {
        boolean matches(T first, T second);
    }

    @Deprecated
    public static void assertEquatableListsMatch(List<? extends Equatable> first, List<? extends Equatable> second) {
        compareLists(first, second, Equatable::isEqual);
    }

    @Deprecated
    public static void assertListsMatchByEquals(List<?> first, List<?> second) {
        compareLists(first, second, Object::equals);
    }

    @Deprecated
    public static <T> void assertListsMatch(List<? extends T> first, List<? extends T> second, Comparator<T> comparator) {
        assertEquals("List sizes must be equal", first.size(), second.size());
        compareLists(first, second, comparator);
    }

    public static void startGobblers(Process process, OutputStream out, OutputStream err, boolean closeOutputStreams) {
        StreamGobbler outGobbler = new StreamGobbler(process.getInputStream(), out, closeOutputStreams);
        StreamGobbler errGobler = new StreamGobbler(process.getErrorStream(), err, closeOutputStreams);
        outGobbler.start();
        errGobler.start();
    }

    private static <T> void compareLists(List<? extends T> first, List<? extends T> second, Comparator<T> comparator) {
        final List<? extends T> copy = new ArrayList<>(second);

        for (T element : first) {
            final int i = copy.indexOf(element);
            assertTrue("Element " + element + " not found in second list", i >=0);
            final T other = copy.get(i);
            assertTrue("Element " + element + " doesn't match " + other + " from second list", comparator.matches(element, other));
            //Remove the element as already validated
            copy.remove(i);
        }

        //See if there are any non-matched elements in second list left
        if (!copy.isEmpty()) fail("Non-matched elements in second list: " + copy);
    }

    /*
            New collection-based methods start here. When comparing collections, don't use old methods anymore!!!
     */

    public static <T> void assertUnorderedCollectionsMatch(Collection<? extends T> first, Collection<? extends T> second) {
        HashMultiset<T> firstSet = HashMultiset.create(first);
        HashMultiset<T> secondSet = HashMultiset.create(second);
        if (!firstSet.equals(secondSet)) fail(getFailMessage(firstSet, secondSet));
    }

    public static <T> void assertOrderedCollectionsMatch(Collection<? extends T> first, Collection<? extends T> second) {
        List<T> firstList = new LinkedList<>(first);
        List<T> secondList = new LinkedList<>(second);
        if (!firstList.equals(secondList)) fail(getFailMessage(firstList, secondList));
    }

    public static <T extends Equatable> void assertUnorderedEquatableCollectionsMatch(Collection<? extends T> first, Collection<? extends T> second) {
        //Prepare wrapped collections
        HashMultiset<EquatableWrapper<T>> firstSet = wrapToHashMultiset(first);
        HashMultiset<EquatableWrapper<T>> secondSet = wrapToHashMultiset(second);

        if (!firstSet.equals(secondSet)) fail(getFailMessage(firstSet, secondSet));
    }

    public static <T extends Equatable> void assertOrderedEquatableCollectionsMatch(Collection<? extends T> first, Collection<? extends T> second) {
        //Prepare wrapped lists (to maintain order)
        List<EquatableWrapper<T>> firstList = wrapToList(first);
        List<EquatableWrapper<T>> secondList = wrapToList(second);

        if (!firstList.equals(secondList)) fail(getFailMessage(first, second));
    }

    public static <K, T extends Equatable> void assertEquatableListMultimapsMatch(ListMultimap<K, T> first, ListMultimap<K, T> second) {
        //Wrap values in Equatable wrapper
        ListMultimap<K, EquatableWrapper<T>> wrappedFirst = wrapToMultimap(first);
        ListMultimap<K, EquatableWrapper<T>> wrappedSecond = wrapToMultimap(second);

        if (!wrappedFirst.equals(wrappedSecond)) fail(getFailMessage(first, second));
    }

    private static <K, T extends Equatable> ListMultimap<K, EquatableWrapper<T>> wrapToMultimap(ListMultimap<K, T> first) {
        ListMultimap<K, EquatableWrapper<T>> result = ArrayListMultimap.create();

        for (Map.Entry<K, T> entry: first.entries()) {
            result.put(entry.getKey(), new EquatableWrapper<T>(entry.getValue()));
        }

        return result;
    }

    private static <T extends Equatable> List<EquatableWrapper<T>> wrapToList(Collection<? extends T> collection) {
        return collection
                .stream()
                .map((Function<T, EquatableWrapper<T>>) EquatableWrapper::new)
                .collect(Collectors.toList());
    }

    private static <T extends Equatable> HashMultiset<EquatableWrapper<T>> wrapToHashMultiset(Collection<? extends T> first) {
        HashMultiset<EquatableWrapper<T>> hms = HashMultiset.create();
        for (T equatable : first) {
            hms.add(new EquatableWrapper<>(equatable));
        }
        return hms;
    }

    private static <T> String getFailMessage(HashMultiset<? extends T> firstSet, HashMultiset<? extends T> secondSet) {
        return "First and second sets do not match, difference first/second: " +
                Multisets.difference(firstSet, secondSet) + ", second/first: " + Multisets.difference(secondSet,
                firstSet);
    }

    private static <T> String getFailMessage(Collection<? extends T> firstList, Collection<? extends T> secondList) {
        List<T> firstCopy = new LinkedList<>(firstList);
        List<T> secondCopy = new LinkedList<>(secondList);

        return "First and second collections do not match, difference first/second: " +
                firstCopy.removeAll(secondList) + ", second/first: " + secondCopy.removeAll(firstList);
    }

    private static <K, T extends Equatable> String getFailMessage(ListMultimap<K, T> first, ListMultimap<K, T> second) {
        Map<K, Collection<T>> firstMap = first.asMap();
        Map<K, Collection<T>> secondMap = second.asMap();

        return "First and second ListMultimaps do not match, difference first/second: " +
                Maps.difference(firstMap, secondMap) + ", second/first: " + Maps.difference(secondMap, firstMap);
    }
    
    public static DateTime parseDateTime(String dateTime) {
        return DateTime.parse(dateTime, FORMATTER);
    }
}
