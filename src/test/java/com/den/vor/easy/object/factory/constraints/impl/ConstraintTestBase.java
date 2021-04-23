/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.factory.constraints.impl;

public class ConstraintTestBase {

    protected class ComparableClass implements Comparable<ComparableClass> {

        @Override
        public int compareTo(ComparableClass o) {
            throw new UnsupportedOperationException("It's supposed to be mocked");
        }
    }
}
