package org.mitash.altdrive.logger;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * @author jacob
 */
public class Log {

    public static Logger LOG(String name) {
        Logger logger = Logger.getLogger(name);
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
        return logger;
    }
}
