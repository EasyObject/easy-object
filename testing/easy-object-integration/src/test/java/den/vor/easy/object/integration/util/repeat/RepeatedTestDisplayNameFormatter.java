/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
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
