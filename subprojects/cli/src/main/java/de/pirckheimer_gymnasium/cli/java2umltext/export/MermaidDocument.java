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

public class MermaidDocument extends Document
{

    @Override
    protected String getHeader()
    {
        return "classDiagram\n";
    }

    @Override
    protected String getFooter()
    {
        return "";
    }

    @Override
    protected String exportClass(ClassWrapper cw)
    {
        String fullname = ((cw.pkg() == null || cw.pkg().isBlank()) ? ""
                : (cw.pkg().replace(".", "_") + "_"))
                + cw.name().replace(".", "_");

        String str = "class " + fullname + " { ";

        if (cw.type().contains("abstract"))
        {
            str += "\n<<abstract>>";
        }
        if (cw.type().contains("interface"))
        {
            str += "\n<<interface>>";
        }
        if (cw.type().contains("enum"))
        {
            str += "\n<<enum>>";
        }
        if (cw.type().contains("record"))
        {
            str += "\n<<record>>";
        }

        str += cw.fields().isEmpty() ? ""
                : "\n" + cw.fields().stream().map(f -> f.visibility().symbol()
                        + " "
                        + (f.type().isBlank() ? ""
                                : f.type().replaceAll("[<>]", "~") + " ")
                        + f.name() + (f.isStatic() ? "$" : ""))
                        .collect(Collectors.joining("\n"));

        str += cw.methods().isEmpty() ? ""
                : "\n" + cw.methods().stream()
                        .map(m -> m.visibility().symbol() + " "
                                + m.returnType().replaceAll("[<>]", "~") + " "
                                + m.name() + "("
                                + m.parameters().stream()
                                        .map(p -> p.replaceAll("[<>]", "~"))
                                        .collect(Collectors.joining(","))
                                + ")" + (m.isAbstract() ? "*" : "")
                                + (m.isStatic() ? "$" : ""))
                        .collect(Collectors.joining("\n"));

        str += "\n}\n";

        return str;
    }

    @Override
    protected String exportRelationship(Relationship r)
    {
        return r.source().replace(".", "_") + " "
                + (r.type().equals("+..") ? "<.." : r.type()) + " "
                + r.target().replace(".", "_")
                + (r.type().equals("+..") ? " : << contains >>" : "");
    }
}
