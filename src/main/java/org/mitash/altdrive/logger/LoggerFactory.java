package org.mitash.altdrive.logger;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jacob Mitash
 */
public class LoggerFactory {

    private final static Level LEVEL_LOGGER_DEFAULT = Level.INFO;
    private final static Level LEVEL_CONSOLE_DEFAULT = Level.INFO;
    private final static Level LEVEL_FILE_DEFAULT = Level.INFO;
    private static boolean loggedLoggerLevelError = false;
    private static boolean loggedConsoleLevelError = false;
    private static boolean loggedFileLevelError = false;

    private final static Logger LOGGER = build(LoggerFactory.class);

    /**
     * Builds a logger with the pretty formatter and output stream to <code>System.out</code>.
     * @param clazz the class to build the logger for
     * @return a logger with custom formatter, output stream, and name
     */
    public static Logger build(Class clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setUseParentHandlers(false);
        PrettyFormatter prettyFormatter = new PrettyFormatter();
        Handler handler = new ConsoleHandler() {
            @Override
            protected void setOutputStream(OutputStream out) throws SecurityException {
                super.setOutputStream(System.out);
            }
        };
        handler.setFormatter(prettyFormatter);
        logger.addHandler(handler);
        logger.setLevel(getLoggerLevel());
        handler.setLevel(getConsoleLevel());
        return logger;
    }

    /**
     * Get's the <code>logger.logger.level</code> or it's default for logging.
     * @return the logging level
     */
    private static Level getLoggerLevel() {
        if (loggedLoggerLevelError) {
            //Prevents spamming multiple of the same error
            return LEVEL_LOGGER_DEFAULT;
        }

        String levelProperty = System.getProperty("logger.logger.level", LEVEL_LOGGER_DEFAULT.getName());
        Level level;
        try {
            level = Level.parse(levelProperty);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid logger logging level: " + levelProperty);
            LOGGER.warning("Defaulting to " + LEVEL_LOGGER_DEFAULT.getName());
            loggedLoggerLevelError = true;
            return LEVEL_LOGGER_DEFAULT;
        }

        return level;
    }

    /**
     * Get's the <code>logger.console.level</code> or it's default for console logging.
     * @return the console logging level
     */
    private static Level getConsoleLevel() {
        if (loggedConsoleLevelError) {
            //Prevents spamming multiple of the same error
            return LEVEL_CONSOLE_DEFAULT;
        }

        String levelProperty = System.getProperty("logger.console.level", LEVEL_CONSOLE_DEFAULT.getName());
        Level level;
        try {
            level = Level.parse(levelProperty);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid console logging level: " + levelProperty);
            LOGGER.warning("Defaulting to " + LEVEL_CONSOLE_DEFAULT.getName());
            loggedConsoleLevelError = true;
            return LEVEL_CONSOLE_DEFAULT;
        }
        return level;
    }

    /**
     * Get's the <code>logger.file.level</code> or it's default for file logging.
     * @return the file logging level
     */
    private static Level getFileLevel() {
        if (loggedFileLevelError) {
            //Prevents spamming multiple of the same error
            return LEVEL_FILE_DEFAULT;
        }

        String levelProperty = System.getProperty("logger.file.level", LEVEL_FILE_DEFAULT.getName());
        Level level;
        try {
            level = Level.parse(levelProperty);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid file logging level: " + levelProperty);
            LOGGER.warning("Defaulting to " + LEVEL_FILE_DEFAULT.getName());
            loggedFileLevelError = true;
            return LEVEL_FILE_DEFAULT;
        }
        return level;
    }
}
