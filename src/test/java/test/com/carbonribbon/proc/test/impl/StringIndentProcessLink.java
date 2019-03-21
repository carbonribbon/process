package test.com.carbonribbon.proc.test.impl;

import com.carbonribbon.proc.ProcessLink;

/**
 * Test {@link ProcessLink}
 */
public class StringIndentProcessLink implements ProcessLink<String> {
    @Override
    public String apply(String s) {
        return " " + s;
    }
}
