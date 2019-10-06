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

package com.gabstudios.manager;

/**
 * 
 * This type of exception is caused by a resource related error within the
 * system. This type of exception is considered non recoverable and an error,
 * but the caller may try to catch this type of exception to recover.
 * 
 * @author Gregory Brown (sysdevone)
 */
public class ManagerSysException extends RuntimeException
{
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5649551798338539648L;
    
    /**
     * Constructor with a message.
     * 
     * @param message
     *            A <code>String</code> message.
     */
    public ManagerSysException(final String message)
    {
        this(message, (Throwable) null);
    }
    
    /**
     * Constructor with both a message and a throwable.
     * 
     * @param message
     *            A <code>String</code> message.
     * @param throwable
     *            An instance of <code>Throwable</code>.
     */
    public ManagerSysException(final String message, final Throwable throwable)
    {
        super(message, null);
    }
    
    /**
     * Constructor with a throwable.
     * 
     * @param throwable
     *            An instance of <code>Throwable</code>.
     */
    public ManagerSysException(final Throwable throwable)
    {
        this(throwable.getMessage(), throwable);
    }
    
}
