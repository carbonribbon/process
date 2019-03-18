package com.carbonribbon.proc;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Allows for chaining together a collection of {@link ProcessLink} to be applied in order to an object.
 *
 * @author Patrik Dallmann (@carbonribbon.com)
 * @version 1.0
 */
public interface ProcessChain<T> {
    /**
     * Adds a {@link ProcessLink} instance to the chain.
     *
     * @param processLink a {@link ProcessLink} instance to add to the chain
     */
    void addLink(ProcessLink<T> processLink);

    /**
     * Adds a {@link ProcessLink} to the chain.
     *
     * @param clazz a {@link ProcessLink} to add to the chain
     */
    void addLink(Class<? extends ProcessLink<T>> clazz);

    /**
     * Adds a conditional {@link ProcessLink} to the chain.
     * The predicate is tested and the link is applied if and only if the predicate is true.
     *
     * @param clazz a {@link ProcessLink} to add to the chain
     * @param test  a {@link Predicate} that will be evaluated before the {@link ProcessLink} is called
     */
    void addLink(Class<? extends ProcessLink<T>> clazz, Predicate<T> test);

    /**
     * Apply the {@link ProcessChain} to an object, applying each {@link ProcessLink} in turn.
     *
     * @param t the object to process
     * @return the resulting object
     */
    T applyChain(T t);

    /**
     * Apply the {@link ProcessChain} to each object in a {@link Collection}, applying each {@link ProcessLink} in turn.
     * The order in which links are applied to objects is unspecified.
     *
     * @param t a collection of objects to process
     * @return a {@link Collection} containing the resulting objects
     */
    Collection<T> applyChain(Collection<T> t);
}
