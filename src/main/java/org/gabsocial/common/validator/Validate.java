/*****************************************************************************************
 * 
 * Copyright 2014 Gregory Brown. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 ***************************************************************************************** 
 */

package org.gabsocial.common.validator;

/**
 * 
 * A class to help validate parameters for development.
 * 
 * @author Gregory Brown (sysdevone)
 */
public class Validate
{
    
    /**
     * Checks if a parameter is null. If null then an
     * <code>IllegalArgumentException</code> is thrown.
     * 
     * @param param
     *            The parameter to verify.
     * @param errorMessage
     *            The error message to include in an exception if it is created.
     * @param clazz
     *            The class throwing the exception.
     * @throws IllegalArgumentException
     *             This exception is thrown if the parameter is null.
     */
    public static <T> void isNull(final T param, final String errorMessage,
            final Class<?> clazz) throws IllegalArgumentException
    {
        if (param == null)
        {
            Validate.throwIllegalArgumentException(errorMessage, clazz);
        }
    }
    
    /**
     * Checks if a parameter is null or empty.
     * 
     * @param param
     *            The parameter to verify.
     * @param errorMessage
     *            The error message to include in an exception if it is created.
     * @param clazz
     *            The class throwing the exception.
     * @throws IllegalArgumentException
     *             This exception is thrown if the parameter is null or empty.
     */
    public static void isNullOrEmpty(final String param,
            final String errorMessage, final Class<?> clazz)
            throws IllegalArgumentException
    {
        if ((param == null) || (param.trim().length() == 0))
        {
            Validate.throwIllegalArgumentException(errorMessage, clazz);
        }
    }
    
    /*
     * Forces an IllegalArgumentException to be thrown.
     * 
     * @param errorMessage The error message to include in an exception if it is
     * created.
     * 
     * @param clazz The class throwing the exception.
     */
    private static void throwIllegalArgumentException(
            final String errorMessage, final Class<?> clazz)
    {
        final StringBuilder message = new StringBuilder();
        message.append(clazz);
        message.append(": ");
        message.append(errorMessage);
        
        throw (new IllegalArgumentException(message.toString()));
    }
}
