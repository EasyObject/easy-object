/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.integration.util.repeat;

import static org.junit.jupiter.api.RepeatedTest.*;

public class RepeatedTestDisplayNameFormatter {

    private final String displayName;

    public RepeatedTestDisplayNameFormatter(String displayName) {
        this.displayName = displayName;
    }

    String format(int currentRepetition, int totalRepetitions) {
        return SHORT_DISPLAY_NAME
                .replace(DISPLAY_NAME_PLACEHOLDER, this.displayName)
                .replace(CURRENT_REPETITION_PLACEHOLDER, String.valueOf(currentRepetition))
                .replace(TOTAL_REPETITIONS_PLACEHOLDER, String.valueOf(totalRepetitions));
    }
}
