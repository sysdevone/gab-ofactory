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

import com.gabstudios.validate.Validate;
import com.gabstudios.manager.PropertiedManager;
import com.gabstudios.manager.PropertiedManageable;


/**
 * 
 * A base implementation to extend from when creating a child that is managed by
 * the <code>PropertiedManager</code>.
 * 
 * @author Gregory Brown (sysdevone)
 */
public abstract class BasePropertiedManageable extends
        BaseManageable implements PropertiedManageable
{
    /*
     * The settings used to initialize this class.
     */
    private Object _settings;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.gabsocial.manager.ManagerChild#initialize(org.gabsocial.manager
     * .Manager, java.lang.String)
     */
    public <P extends PropertiedManager, S> void initialize(final P parent,
            final String key, S settings)
    {
    	Validate.defineObject(parent).testNotNull().throwValidationExceptionOnFail().validate();
    	Validate.defineString(key).testNotNullEmpty().throwValidationExceptionOnFail().validate();
    	Validate.defineObject(settings).testNotNull().throwValidationExceptionOnFail().validate();
        
        super.initialize(parent, key);
        this._settings = settings;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.gabsocial.manager.PropertiedManagerChild#getSettings()
     */
    public <S> S getSettings()
    {
        assert( this._settings != null ) : "The settings should not be NULL.";
        return (S) (this._settings);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BasePropertiedManagerChildImpl [toString()=");
        builder.append(super.toString());
        builder.append(", _settings=");
        builder.append(this._settings);
        builder.append("]");
        return builder.toString();
    }
    
}
