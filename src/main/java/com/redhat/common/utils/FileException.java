/*
 * Copyright (C) 2017 Scot P. Floess
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.redhat.common.utils;

/**
 * Raised if any problems arise dealing with a file.
 *
 * @author Scot P. Floess
 */
public class FileException extends RuntimeException {

    /**
     * Default constructor.
     */
    public FileException() {

    }

    /**
     * Sets the message.
     *
     * @param message detail message.
     */
    public FileException(final String message) {
        super(message);
    }

    /**
     * Sets the cause of why self is being raised.
     *
     * @param cause the cause of why self is being raised.
     */
    public FileException(final Throwable cause) {
        super(cause);
    }

    /**
     * Sets the cause of why self is raised and a message about it.
     *
     * @param message detail message.
     * @param cause   the cause of why self is being raised.
     */
    public FileException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
