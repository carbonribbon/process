package com.carbonribbon.proc;

/**
 * Default implementation of {@link ProcessLinkFactory}.
 * Requires {@link ProcessLink} classes to have a default constructor.
 *
 * @author Patrik Dallmann (@carbonribbon.com)
 * @version 1.0
 */
public class DefaultProcessLinkFactory implements ProcessLinkFactory {
    @Override
    public ProcessLink newProcessLink(Class<? extends ProcessLink> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
