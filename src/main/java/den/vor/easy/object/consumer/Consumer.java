package den.vor.easy.object.consumer;

import den.vor.easy.object.consumer.formatter.Formatter;
import den.vor.easy.object.value.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class Consumer<T> {

    public static final int DEFAULT_BATCH_SIZE = 10_000;
    private Formatter<T>[] formatters;
    private InternalConsumer internalConsumer;

    public Consumer(int batchSize, Formatter<T>... formatters) {
        this(batchSize, true, formatters);
    }

    public Consumer(boolean useBatch, Formatter<T>... formatters) {
        this(DEFAULT_BATCH_SIZE, useBatch, formatters);
    }

    private Consumer(int batchSize, boolean useBatch, Formatter<T>... formatters) {
        this.formatters = formatters;
        this.internalConsumer = useBatch ? new BatchConsumer(batchSize) : new SimpleConsumer();
    }

    public Consumer(Formatter<T>... formatters) {
        this(DEFAULT_BATCH_SIZE, formatters);
    }

    public void consume(Value<?> value) {
        internalConsumer.consume(value);
    }

    public void flush() {
        internalConsumer.flush();
    }

    protected abstract void doConsume(T toConsume);

    private interface InternalConsumer {
        void consume(Value<?> toConsume);

        default void flush(){

        }
    }

    private class SimpleConsumer implements InternalConsumer {

        @Override
        public void consume(Value<?> toConsume) {
            for (Formatter<T> formatter : formatters) {
                Consumer.this.doConsume(formatter.format(toConsume));
            }
        }
    }

    private class BatchConsumer implements InternalConsumer {

        private final int batchSize;

        private List<Value<?>> batch = new ArrayList<>();

        private BatchConsumer(int batchSize) {
            this.batchSize = batchSize;
        }

        public void consume(Value<?> toConsume) {
            batch.add(toConsume);
            if (batch.size() == batchSize) {
                List<Value<?>> fullBatch = batch;
                batch = new ArrayList<>();
                doConsumeBulk(fullBatch);
            }
        }

        @Override
        public void flush() {
            doConsumeBulk(batch);
        }

        private void doConsumeBulk(List<Value<?>> batch) {
            for (Formatter<T> formatter : formatters) {
                T formatted = formatter.format(batch);
                Consumer.this.doConsume(formatted);
            }
        }
    }
}
