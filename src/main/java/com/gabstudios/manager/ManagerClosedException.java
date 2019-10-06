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
 * This exception is thrown when a method is called on either a closed Manager
 * or Manager's child.
 * 
 * @author Gregory Brown (sysdevone)
 */
public class ManagerClosedException extends ManagerSysException
{
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 467226162251095952L;
    
    /**
     * Constructor with a message.
     * 
     * @param message
     *            A <code>String</code> message.
     */
    public ManagerClosedException(final String message)
    {
        super(message);
    }
    
    /**
     * Constructor with both a message and a throwable.
     * 
     * @param message
     *            A <code>String</code> message.
     * @param throwable
     *            An instance of <code>Throwable</code>.
     */
    public ManagerClosedException(final String message,
            final Throwable throwable)
    {
        super(message, throwable);
    }
    
    /**
     * Constructor with a throwable.
     * 
     * @param throwable
     *            An instance of <code>Throwable</code>.
     */
    public ManagerClosedException(final Throwable throwable)
    {
        super(throwable);
    }
    
}
