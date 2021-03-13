/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.integration.util.repeat;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import java.util.List;

import static java.util.Collections.emptyList;

class RepeatedTestInvocationContext implements TestTemplateInvocationContext {

    private final int currentRepetition;
    private final int totalRepetitions;
    private final RepeatedTestDisplayNameFormatter formatter;

    public RepeatedTestInvocationContext(int currentRepetition, int totalRepetitions,
                                         RepeatedTestDisplayNameFormatter formatter) {

        this.currentRepetition = currentRepetition;
        this.totalRepetitions = totalRepetitions;
        this.formatter = formatter;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        return this.formatter.format(this.currentRepetition, this.totalRepetitions);
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        return emptyList();
    }

}
