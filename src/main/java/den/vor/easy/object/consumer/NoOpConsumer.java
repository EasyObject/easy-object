package den.vor.easy.object.consumer;

import den.vor.easy.object.consumer.formatter.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOpConsumer<T> extends Consumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoOpConsumer.class);

    public NoOpConsumer(Formatter<T>... formatters) {
        super(formatters);
    }

    @Override
    protected void doConsume(T toConsume) {
        LOGGER.debug("Consuming a value");
        System.out.println("Consuming a value");
    }
}
