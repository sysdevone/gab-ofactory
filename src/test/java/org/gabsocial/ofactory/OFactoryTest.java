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

package org.gabsocial.ofactory;

import org.gabsocial.ofactory.impl.MockOFactoryChildImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * Test class for the <code>OFactory</code>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class OFactoryTest
{
    
    OFactory _ofactory;
    
    @Before
    public void setup()
    {
        this._ofactory = new OFactory();
    }
    
    @Test
    public void testCreateChildWithClassKey()
    {
        Assert.assertTrue(this._ofactory != null);
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        try
        {
            final OFactoryChild child = this._ofactory
                    .create(MockOFactoryChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
            
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithClassNameKey()
    {
        Assert.assertTrue(this._ofactory != null);
        
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        try
        {
            final OFactoryChild child = this._ofactory.create(className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClass()
    {
        Assert.assertTrue(this._ofactory != null);
        
        final String key = "test-mock-o";
        
        try
        {
            final OFactoryChild child = this._ofactory.create(key,
                    MockOFactoryChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClassName()
    {
        Assert.assertTrue(this._ofactory != null);
        
        final String key = "test-mock-o";
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        try
        {
            final OFactoryChild child = this._ofactory.create(key, className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testFactoryCreation()
    {
        Assert.assertTrue(this._ofactory != null);
    }
    
    @Test
    public void testGetChildWithClassNameKey()
    {
        
        Assert.assertTrue(this._ofactory != null);
        
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        try
        {
            this._ofactory.create(className);
            
            final String key = MockOFactoryChildImpl.class.getName();
            final OFactoryChild child = this._ofactory.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testGetChildWithKey()
    {
        
        Assert.assertTrue(this._ofactory != null);
        
        final String key = "test-mock-o";
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        try
        {
            this._ofactory.create(key, className);
            
            final OFactoryChild child = this._ofactory.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testOFactoryClose()
    {
        this._ofactory.close();
        
        try
        {
            final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
            boolean isFound = this._ofactory.containsChild(className);
            System.out.println("isFound=" + isFound);
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
            MockOFactoryChildImpl child = this._ofactory.create(className,
                    className);
            System.out.println("Child=" + child);
            Assert.fail();
        }
        catch (OFactoryChildException e)
        {
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
            MockOFactoryChildImpl child = this._ofactory.create(className,
                    MockOFactoryChildImpl.class);
            System.out.println("Child=" + child);
            Assert.fail();
        }
        catch (OFactoryChildException e)
        {
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            MockOFactoryChildImpl child = this._ofactory
                    .create(MockOFactoryChildImpl.class);
            System.out.println("Child=" + child);
            Assert.fail();
        }
        catch (OFactoryChildException e)
        {
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
            this._ofactory.closeChild(className);
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
            this._ofactory.get(className);
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            this._ofactory.getChildCount();
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        // test getKeys()
        try
        {
            this._ofactory.getKeys();
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        // test isClose().
        boolean isClosed = this._ofactory.isClosed();
        Assert.assertTrue(isClosed);
        
        
        // test repeated close();
        try
        {
            this._ofactory.close();
            Assert.fail();
        }
        catch (OFactoryClosedException e)
        {
            Assert.assertTrue(true);
        }
    }
}
