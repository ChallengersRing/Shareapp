package in.shareapp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoPrinter {

    private static final Logger logger = LoggerFactory.getLogger(LogoPrinter.class);

    public static void printLogo() {
        String logo = """
                
                     _______. __    __       ___      .______       _______     ___      .______   .______  \s
                    /       ||  |  |  |     /   \\     |   _  \\     |   ____|   /   \\     |   _  \\  |   _  \\ \s
                   |   (----`|  |__|  |    /  ^  \\    |  |_)  |    |  |__     /  ^  \\    |  |_)  | |  |_)  |\s
                    \\   \\    |   __   |   /  /_\\  \\   |      /     |   __|   /  /_\\  \\   |   ___/  |   ___/ \s
                .----)   |   |  |  |  |  /  _____  \\  |  |\\  \\----.|  |____ /  _____  \\  |  |      |  |     \s
                |_______/    |__|  |__| /__/     \\__\\ | _| `._____||_______/__/     \\__\\ | _|      | _|     \s
                                                                                                            \s
                                                                                                by: abhay
                """;

        logger.info(logo);
    }
}
