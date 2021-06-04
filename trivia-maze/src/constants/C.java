package constants;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

/**
 * Class to hold constants and stuff.
 */
public final class C {
    /** Default border. */
    public static final Border BORDER = new EmptyBorder(10, 10, 10, 10);
    /** Max value of progress bar. */
    public static final int MAX_PROGRESS = 100;
    /** Heading 1 size. */
    public static final int H1 = 48;
    /** Heading 2 size. */
    public static final int H2 = 36;
    /** Heading 3 size. */
    public static final int H3 = 24;
    /** Heading 4 size. */
    public static final int H4 = 16;
    /** Preferred Window Width. */
    public static final int WIDTH = 680;
    /** Preferred Window Height. */
    public static final int HEIGHT = 480;
    /** Default path to open in. */
    public static final String PATH = "./";
    /** Default Padding. */
    public static final int PADDING = 20;
    /** Default gain. */
    public static final float GAIN = -20;
    /** Default Insets. */
    public static final Insets INSET = new Insets(PADDING, PADDING, PADDING, PADDING);

    private C() { }

    /**
     * Wrap a string in HTML tags (Usually for proper wrapping in C).
     * @param toWrap String to be wrapped.
     * @return HTML wrapped string
     */
    public static String wrapHTML(String toWrap) { return "<html>" + toWrap + "</html>"; }
}
