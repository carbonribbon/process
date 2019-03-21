package test.com.carbonribbon.proc.test.impl;

import com.carbonribbon.proc.ProcessLink;

/**
 * Test {@link ProcessLink}
 */
public class StringPadProcessLink implements ProcessLink<String> {

    private final String paddingString;

    public StringPadProcessLink(String paddingString) {
        this.paddingString = paddingString;
    }

    @Override
    public String apply(String s) {
        return paddingString + s;
    }
}
