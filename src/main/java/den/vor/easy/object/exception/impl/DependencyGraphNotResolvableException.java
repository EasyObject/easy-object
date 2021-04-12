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
 * Exception that is expected to be thrown when object fields creation order can not be resolved.
 * See {@link den.vor.easy.object.util.Graph} for more information.
 */
public class DependencyGraphNotResolvableException extends EasyObjectException {

    private final List<?> resolved;
    private final List<?> notResolved;

    /**
     * Creates a new exception object instance.
     * @param resolved list of vertexes for which creation order is resolved
     * @param notResolved list of vertexes for which creation order can not be resolved
     */
    public DependencyGraphNotResolvableException(List<?> resolved, List<?> notResolved) {
        this.resolved = resolved;
        this.notResolved = notResolved;
    }

    @Override
    public String toString() {
        return "DependencyGraphNotResolvableException{" +
                "resolved=" + resolved +
                ", notResolved=" + notResolved +
                '}';
    }
}
