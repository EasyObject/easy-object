package den.vor.easy.object.integration.util.repeat;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.platform.commons.util.AnnotationUtils.isAnnotated;

public class RepeatedTestExtension implements TestTemplateInvocationContextProvider {

    public static final int DEFAULT_ROUNDS = 100;
    public static final String ROUNDS_ENV = "EL_TEST_ROUNDS";
    private final int rounds;

    public RepeatedTestExtension() {
        String testRounds = System.getenv(ROUNDS_ENV);
        this.rounds = testRounds == null ? DEFAULT_ROUNDS : Integer.parseInt(testRounds);
    }

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return isAnnotated(context.getTestMethod(), RepeatedElTest.class);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        String displayName = context.getDisplayName();
        RepeatedElTest repeatedTest = AnnotationUtils.findAnnotation(testMethod, RepeatedElTest.class).orElseThrow();
        int totalRepetitions = totalRepetitions(repeatedTest);
        RepeatedTestDisplayNameFormatter formatter = new RepeatedTestDisplayNameFormatter(displayName);

        return IntStream
                .rangeClosed(1, totalRepetitions)
                .mapToObj(repetition -> new RepeatedTestInvocationContext(repetition, totalRepetitions, formatter));
    }

    private int totalRepetitions(RepeatedElTest repeatedTest) {
        int repetitions = repeatedTest.value();
        return repetitions > 0 ? repetitions : rounds;
    }

}
