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
package de.pirckheimer_gymnasium.cli.java2umltext.export;

import java.util.stream.Collectors;

import de.pirckheimer_gymnasium.cli.java2umltext.model.ClassWrapper;
import de.pirckheimer_gymnasium.cli.java2umltext.model.Document;
import de.pirckheimer_gymnasium.cli.java2umltext.model.Relationship;

public class PlantUMLDocument extends Document
{

    @Override
    protected String getHeader()
    {
        return "@startuml\n";
    }

    @Override
    protected String getFooter()
    {
        return "@enduml";
    }

    @Override
    protected String exportClass(ClassWrapper cw)
    {
        String fullname = ((cw.pkg() == null || cw.pkg().trim().equals("")) ? ""
                : (cw.pkg() + ".")) + cw.name();

        String str = (cw.type() == "record" ? "class" : cw.type()) + " "
                + fullname + (cw.type() == "record" ? " <<record>>" : "")
                + " {";

        str += cw.fields().isEmpty() ? ""
                : "\n" + cw.fields().stream()
                        .map(f -> f.visibility().symbol() + " "
                                + (f.isStatic() ? "{static} " : "")
                                + (f.type().isBlank() ? "" : f.type() + " ")
                                + f.name())
                        .collect(Collectors.joining("\n"));

        str += cw.methods().isEmpty() ? ""
                : "\n" + cw.methods().stream()
                        .map(m -> m.visibility().symbol() + " "
                                + (m.isStatic() ? "{static} " : "")
                                + (m.isAbstract() ? "{abstract} " : "")
                                + m.returnType() + " " + m.name() + "("
                                + m.parameters().stream()
                                        .collect(Collectors.joining(","))
                                + ")")
                        .collect(Collectors.joining("\n"));

        str += "\n}\n";

        return str;
    }

    @Override
    protected String exportRelationship(Relationship r)
    {
        return r.source() + " " + r.type() + " " + r.target();
    }
}
