package org.mitash.altdrive.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 *
 * @author jacob
 */
public class PrettyFormatter extends Formatter {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        //Timestamp
        Date date = new Date(record.getMillis());
        builder.append(DATE_FORMAT.format(date));

        builder.append(" ");

        //Logging level
        builder.append("[");
        String logLevel = "";
        String logColor = "";
        if(record.getLevel().equals(Level.FINER) || record.getLevel().equals(Level.FINE) || record.getLevel().equals(Level.FINEST)) {
            logLevel = record.getLevel().toString();
        } else if(record.getLevel().equals(Level.CONFIG)) {
            logLevel = "CONF";
        } else if(record.getLevel().equals(Level.INFO)) {
            logLevel = record.getLevel().toString();
        } else if(record.getLevel().equals(Level.WARNING)) {
            logColor = ANSI_YELLOW;
            logLevel = "WARN";
        } else if(record.getLevel().equals(Level.SEVERE)) {
            logColor = ANSI_RED;
            logLevel = "ERROR";
        }
        builder.append(logColor);
        builder.append(String.format("%5s", logLevel));
        builder.append(ANSI_RESET);
        builder.append("]");

        builder.append(" ");

        //Name of logger
        if(record.getLevel().intValue() < Level.WARNING.intValue()) {
            builder.append(ANSI_CYAN);
        } else {
            builder.append(logColor);
        }
        builder.append(record.getLoggerName());
        builder.append(ANSI_RESET);

        builder.append(" ");

        //Actual message
        builder.append(formatMessage(record));

        builder.append("\n");

        return builder.toString();
    }
}
