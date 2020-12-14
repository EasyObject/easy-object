package den.vor.easy.object.consumer;

import den.vor.easy.object.consumer.formatter.Formatter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileConsumer extends Consumer<String> {

    private final String fileName;

    public FileConsumer(int batchSize, String fileName, Formatter<String>... formatters) {
        super(batchSize, formatters);
        this.fileName = fileName;
    }

    public FileConsumer(boolean useBatch, String fileName, Formatter<String>... formatters) {
        super(useBatch, formatters);
        this.fileName = fileName;
    }

    public FileConsumer(String fileName, Formatter<String>... formatters) {
        super(formatters);
        this.fileName = fileName;
    }

    @Override
    protected void doConsume(String toConsume) {
        Path path = Paths.get(fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, toConsume + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
