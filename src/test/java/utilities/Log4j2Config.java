//package utilities;
//
//import ch.qos.logback.classic.LoggerContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.Serializable;
//public class Log4j2Config {
//
////    public static final Logger logger = LogManager.getLogger(Log4j2Config.class);
//    private static final Logger LOGGER = LoggerFactory.getLogger(LaunchUrlLogging.class);
//
//    public Log4j2Config() {
//        String patternLayout;
//        LoggerContext ctx = (LoggerContext) LoggerFactory.getContext(false);
//        Configuration config = ctx.getConfiguration();
//        patternLayout = "%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level" + " - %msg%n";
//        Layout<? extends Serializable> layout = PatternLayout.newBuilder()
//                .withPattern(patternLayout)
//                .build();
//
//        Appender appender = FileAppender.newBuilder()  //---creating the Appender
//                .withName("LogFile")
//                .withFileName(PathAndVariable.log_name + "/" + PathAndVariable.tags.split("@")[1] + ".log")
//                .withAppend(false)
//                .withImmediateFlush(false)
//                .withLayout(layout)
//                .build();
//        appender.start();
//        config.addAppender(appender);
//    }
//
//    public void activeLoggerConsole(boolean state) {  //Remove or Add Console Logger
//        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//        Configuration config = ctx.getConfiguration();
//        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
//        if (!state) {
//            loggerConfig.removeAppender("Console");
//        } else {
//            Appender appender = config.getAppender("Console");
//            loggerConfig.addAppender(appender, null, null);
//        }
//        ctx.updateLoggers();
//    }
//
//    public void activeLoggerLogFile(boolean state) {        //Remove or Add LogFile Logger
//        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//        Configuration config = ctx.getConfiguration();
//        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
//        if (!state) {
//            loggerConfig.removeAppender("LogFile");
//        } else {
//            Appender appender = config.getAppender("LogFile");
//            loggerConfig.addAppender(appender, null, null);
//        }
//        ctx.updateLoggers();
//    }
//}