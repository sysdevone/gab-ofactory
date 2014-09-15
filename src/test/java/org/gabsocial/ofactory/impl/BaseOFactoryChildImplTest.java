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

package org.gabsocial.ofactory.impl;

import org.gabsocial.ofactory.OFactory;
import org.gabsocial.ofactory.OFactoryChild;
import org.gabsocial.ofactory.OFactoryChildException;
import org.gabsocial.ofactory.OFactoryClosedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * A test class for the <code>BaseOFactoryChildImpl</code>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class BaseOFactoryChildImplTest
{
    private OFactory      _ofactory;
    private OFactoryChild _child;
    
    @Before
    public void setup()
    {
        this._ofactory = new OFactory();
        try
        {
            this._child = this._ofactory.create(MockOFactoryChildImpl.class);
            
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testOFactoryChild()
    {
        
        Assert.assertTrue(this._child != null);
        Assert.assertTrue(this._child instanceof MockOFactoryChildImpl);
        
        // test getKey();
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        Assert.assertTrue(className.equals(this._child.getKey()));
        
        // test getParent();
        final OFactory parent = this._child.getParent();
        Assert.assertTrue(this._ofactory == parent);
        
    }
    
    @Test(expected = OFactoryClosedException.class)
    public void testOFactoryChildClose()
    {
        
        Assert.assertTrue(this._child != null);
        Assert.assertTrue(this._child instanceof MockOFactoryChildImpl);
        
        // test close()
        this._child.close();
        
        // tests should throw OFactoryClosedException when a method on the
        // closed child is called.
        try
        {
            String key = this._child.getKey();
            System.out.println("Key=" + key);
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        // test getParent();
        try
        {
            OFactory parent = this._child.getParent();
            System.out.println("Parent=" + parent);
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        // test repeated close();
        try
        {
            this._child.close();
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
    }
}
