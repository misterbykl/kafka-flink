package org.misterbykl.json;


/**
 * The type Json template.
 * <p>
 * misterbykl
 * <p>
 * 31/7/17 11:14
 */
@SuppressWarnings("unused")
public class JsonTemplate {

    private String seperator = ",";

    /**
     * Instantiates a new Json template.
     *
     * @param seperator the seperator
     *
     * misterbykl
     * <p>
     * 31/07/17 11:40
     */
    public JsonTemplate(String seperator) {
        this.seperator = seperator;
    }

    /**
     * Json parser string [ ].
     *
     * @param value the value
     * @return the string [ ]
     * <p>
     * misterbykl
     * <p>
     * 31/7/17 11:17
     */
    private String[] jsonParser(String value) {
        return value.split(this.seperator);
    }

    /**
     * Create org.misterbykl.json array string [ ].
     *
     * @param value the value
     * @return the string [ ]
     * <p>
     * misterbykl
     * <p>
     * 31/7/17 11:17
     */
    public String[] createJsonArray(String value) {
        return this.jsonParser(value);
    }
}
