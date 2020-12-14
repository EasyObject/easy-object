package den.vor.easy.object.facade;

import den.vor.easy.object.consumer.FileConsumer;
import den.vor.easy.object.consumer.StdConsumer;
import den.vor.easy.object.consumer.formatter.Formatter;
import den.vor.easy.object.consumer.formatter.JsonFormatter;

public class ConsumerFacade {

    public static FileConsumer toFile(String path, Formatter<String>... formatters) {
        return new FileConsumer(path, formatters);
    }

    public static StdConsumer toStd(Formatter<String>... formatters) {
        return new StdConsumer(formatters);
    }

    public static StdConsumer toStd(boolean useBatch, Formatter<String>... formatters) {
        return new StdConsumer(useBatch, formatters);
    }

    public static JsonFormatter toJson() {
        return new JsonFormatter();
    }

    public static JsonFormatter toJson(String path) {
        return new JsonFormatter(path);
    }
}
