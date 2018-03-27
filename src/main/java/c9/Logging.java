package c9;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    /**
     * Many legacy programs dating back to Java 1.3 and earlier still use third-party logging libraries such as log4j or
     * Apache Commons Logging, but the java.util.logging package available since Java 1.4 suffices for most needs.
     * Choosing it avoids a lot of complex third-party dependencies.
     *
     * Although you can load a logger on demand, it's usually easiest to just create one per class like so:
     */
     private final static Logger auditLogger = Logger.getLogger("requests");

    public static void main(String[] args) {
    /**
     * Loggers are thread safe, so there's no problem storing them in a shared static field. Indeed, they almost have to
     * be because even if the Logger object were not shared between threads, the logfile or database would be. This is
     * important in highly multithreaded servers.
     *
     * This example outputs to a log named "requests." Multiple Logger objects can output to the same log, but each logger
     * always logs to exactly one log. What and where the log is depends on external configuration. Most commonly it's
     * a file, which may or may not be named "requests"; but it can be a database, a SOAP service running on a different
     * server, another Java program on the same host, or something else.
     *
     * Once you have a logger, you can write to it using any of several methods. The most basic is log(). For example,
     * this catch block logs an unexpected runtime exception at the highest level:
     */

        try {
        } catch (RuntimeException ex) {
            auditLogger.log(Level.SEVERE, "unexpected error " + ex.getMessage(), ex);
        }
        /**
         * Including the exception instead of just a message is optional but customary when logging from a catch block.
         *
         * There are seven levels defined as named constants in java.util.logging.Level in descending order of seriousness:
         * Level.SEVERE (highest value)
         * Level.WARNING
         * Level.INFO
         * Level.CONFIG
         * Level.FINE
         * Level.FINER
         * Level.FINEST (lowest value)
         *
         * I use info for audit logs and warning or severe for error logs. Lower levels are for debugging only and should
         * not be used in production systems. Info, severe, and warning all have convenience helper methods that log at that
         * level. For example, this statement logs a hit including the date and the remote address:
         *
         * logger.info(new Date() + " " + connection.getRemoteSocketAddress());
         *
         * You can use any format that's convenient for the individual log records. Generally, each record should contain
         * a timestamp, the client address, and any information specific to the request that was being processed. If the
         * log message represents an error, include the specific exception that was thrown. Java fills in the location
         * in the code where the message was logged automatically, so you don't need to worry about that.
         */

        /**
         * LoggingDaytimeServer shows a server that logs requests and errors
         */

        /**
         *   The java.util.logging.config.file system property points to a file in the normal properties format that controls
         * the logging. You set this property by passing the -Djava.util.logging.config.file=_filename_ argument when launching
         * the virtual machine. For instance, in Mac OS X, it might be set in the VMOptions in the Info.plist file:
         *
         *
         <key>Java</key>
         <dict>
         <key>VMOptions</key>
         <array>
         <string>-Djava.util.logging.config.file=/opt/daytime/logging.properties
         </string>
         </array>
         </dict>

         /**
         * logging.properties is a sample logging properties file that specifies:
         * Logs should be written to a file.
         * The requests log should be in /tmp/logs/daytime/requests.log at level Info.
         * The errors log should be in /tmp/logs/daytime/requests.log at level Severe.
         * Limit the log size to about 10 megabytes, then rotate.
         * Keep two logs: the current one and the previous one.
         * Use the basic text formatter (not XML).
         * Each line of the logfile should be in the form level message timestamp.
         * Example 9-7. A logging properties file
         */

    }

}
