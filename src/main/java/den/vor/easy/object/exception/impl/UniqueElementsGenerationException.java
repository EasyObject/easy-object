/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.exception.impl;

import den.vor.easy.object.exception.EasyObjectException;

import java.util.List;

/**
 * Exception that is thrown when factory can not generate enough unique values.
 */
public class UniqueElementsGenerationException extends EasyObjectException {

    private int elementsNeeded;
    private int elementsGenerated;
    private int tries;

    /**
     * Creates a new exception instance.
     * @param elementsNeeded number of values, that factory needed to generate
     * @param elementsGenerated number of values, that factory generated
     * @param tries how many times factory generated a value to get unique
     */
    public UniqueElementsGenerationException(int elementsNeeded, int elementsGenerated, int tries) {
        this.elementsNeeded = elementsNeeded;
        this.elementsGenerated = elementsGenerated;
        this.tries = tries;
    }

    @Override
    public String toString() {
        return "UniqueElementsGenerationException{" +
                "elementsNeeded=" + elementsNeeded +
                ", elementsGenerated=" + elementsGenerated +
                ", tries=" + tries +
                '}';
    }
}
