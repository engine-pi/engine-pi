/*
 * MIT License
 *
 * Copyright (c) 2022 Daniel Feitosa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.pirckheimer_gymnasium.engine_pi_cli.java2umltext.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Document implements UML
{

    protected List<ClassWrapper> classList = new ArrayList<>();

    protected List<Relationship> relationshipList = new ArrayList<>();

    public ClassWrapper addClass(String pkg, String type, String name)
    {
        ClassWrapper c = new ClassWrapper(this, pkg, type, name);
        classList.add(c);
        return c;
    }

    public void addRelationship(Relationship r)
    {
        if (relationshipList.stream()
                .noneMatch(i -> r.source().equals(i.source())
                        && r.target().equals(i.target())))
        {
            relationshipList.add(r);
        }
    }

    /**
     * Remove relationships involving classes that are not declared in the
     * Document.
     */
    public void removeForeignRelations()
    {
        relationshipList.removeIf(r -> classList.stream()
                .noneMatch(c -> r.source().equals(c.name()))
                || classList.stream()
                        .noneMatch(c -> r.target().equals(c.name())));
    }

    public String export()
    {
        return getHeader()
                + classList.stream().map(c -> exportClass(c))
                        .collect(Collectors.joining("\n"))
                + "\n"
                + relationshipList.stream().map(r -> exportRelationship(r))
                        .collect(Collectors.joining("\n"))
                + "\n" + getFooter();
    }

    protected abstract String getHeader();

    protected abstract String getFooter();

    protected abstract String exportClass(ClassWrapper cw);

    protected abstract String exportRelationship(Relationship r);
}
