/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.integration.el.visitor;

import org.junit.jupiter.api.Test;

class IdempotentFunctionCallsFoldingVisitorTest {

    @Test
    void shouldFoldCastFunction_whenArgumentIsConst() {
        VisitorTestEvaluator.evaluate("int('12')")
                .expectValue(12);
    }
}
