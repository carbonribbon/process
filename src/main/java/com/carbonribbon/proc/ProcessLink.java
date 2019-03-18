package com.carbonribbon.proc;

/**
 * A {@link ProcessLink} is a processing element that takes on object, performs some processing operation on it and returns the result.
 * {@link ProcessLink}s can be chained together using a {@link ProcessChain}.
 * <p>
 * {@link ProcessLink} implementations must have a default constructor.
 */
public interface ProcessLink<T> {

    /**
     * Applies the {@link ProcessLink} operation to an object.
     * The original object should not be modified.
     *
     * @param t the input object
     * @return the processing result
     */
    T apply(final T t);
}
