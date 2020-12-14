package den.vor.easy.object.consumer;

import den.vor.easy.object.consumer.formatter.Formatter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StdConsumer extends Consumer<String> {

    public StdConsumer(int batchSize, Formatter<String>... formatters) {
        super(batchSize, formatters);
    }

    public StdConsumer(boolean useBatch, Formatter<String>... formatters) {
        super(useBatch, formatters);
    }

    public StdConsumer(Formatter<String>... formatters) {
        super(formatters);
    }

    @Override
    protected void doConsume(String toConsume) {
        System.out.println(toConsume);
    }
}
