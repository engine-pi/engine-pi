/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/ResourcesContainerListener.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package rocks.friedrich.engine_omega.resources;

/**
 * This listener provides callbacks to observe {@code ResourcesContainer}
 * instances.
 *
 * @param <T> The type of the resource that is managed by the container.
 *
 * @see ResourcesContainer
 *
 */
public interface ResourcesContainerListener<T>
        extends ResourcesContainerClearedListener
{
    /**
     * This method gets called after the {@code ResourcesContainer.add} method
     * was executed.
     *
     * @param resourceName The name by which the added resource is identified.
     * @param resource     The added resource.
     * @see ResourcesContainer#add(String, Object)
     */
    T added(String resourceName, T resource);

    /**
     * This method gets called after the {@code ResourcesContainer.remove}
     * method was executed.
     *
     * @param resourceName The name by which the removed resource was
     *                     identified.
     * @param resource     The removed resource.
     * @see ResourcesContainer#remove(String)
     */
    default void removed(String resourceName, T resource)
    {
    }

    default void cleared()
    {
    }
}
