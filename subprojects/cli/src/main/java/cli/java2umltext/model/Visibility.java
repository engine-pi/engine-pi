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
package cli.java2umltext.model;

public enum Visibility
{
    PUBLIC('+'),
    PRIVATE('-'),
    PROTECTED('#'),
    DEFAULT('~');

    private final char symbol;

    Visibility(char symbol)
    {
        this.symbol = symbol;
    }

    public char symbol()
    {
        return symbol;
    }

    public static Visibility fromString(String name)
    {
        name = name.trim().toUpperCase();
        if (name.equals(Visibility.PRIVATE.name()))
        {
            return Visibility.PRIVATE;
        }
        else if (name.equals(Visibility.PROTECTED.name()))
        {
            return Visibility.PROTECTED;
        }
        else if (name.equals(Visibility.PUBLIC.name()))
        {
            return Visibility.PUBLIC;
        }
        else if (name.equals(Visibility.DEFAULT.name()))
        {
            return Visibility.DEFAULT;
        }
        return null;
    }
}
