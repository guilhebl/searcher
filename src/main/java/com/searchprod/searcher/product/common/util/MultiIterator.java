package com.searchprod.searcher.product.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MultiIterator<E> implements Iterator {

    private List<Iterator<E>> iterators;
    private int current;

    public MultiIterator(List<Iterator<E>> iterators) {
        this.iterators = iterators;
        current = 0;
    }
    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return !iterators.isEmpty() && iterators.get(current).hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public E next() {
        E next;
        try {
            next = iterators.get(current).next();
            Iterator<E> iter = iterators.remove(current);
            if (iter.hasNext()) {
                iterators.add(iter);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        return next;
    }

}