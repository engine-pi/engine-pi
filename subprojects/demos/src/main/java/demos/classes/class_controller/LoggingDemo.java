/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package demos.classes.class_controller;

import static pi.Controller.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import pi.Controller;
import pi.Scene;

/**
 * Demonstriert das statische Attribut {@link Controller#logger} und allgemein
 * das Logging in Java.
 *
 * @since 0.45.0
 */
public class LoggingDemo extends Scene
{
    private static final Logger customLogger = Logger
        .getLogger(LoggingDemo.class.getName());

    public LoggingDemo()
    {
        logger.severe("Eine SEVERE-Nachricht!");
        logger.warning("Eine WARNING-Nachricht!");
        logger.info("Eine INFO-Nachricht!");
        logger.config("Eine CONFIG-Nachricht!");
        logger.fine("Eine FINE-Nachricht!");
        logger.finer("Eine FINER-Nachricht!");
        logger.finest("Eine FINEST-Nachricht!");

        logger.log(Level.SEVERE,
            "Eine SEVERE-Nachricht über die allgemeine log-Methode");

        customLogger
            .severe("Eine SEVERE-Nachricht des benutzerdefinierten Loggers!");
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new LoggingDemo());
    }
}
