package com.carbonribbon.proc;

/**
 * Interface defining a factory class for providing {@link ProcessLink} instances by class.
 *
 * @author Patrik Dallmann (@carbonribbon.com)
 * @version 1.0
 */
public interface ProcessLinkFactory {
    /**
     * Provides a new {@link ProcessLink} instance for a given class.
     *
     * @param clazz {@link ProcessLink} class
     * @return new instance of clazz
     */
    ProcessLink newProcessLink(Class<? extends ProcessLink> clazz);
}
