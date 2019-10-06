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

import com.gabstudios.manager.impl.MockManagerChildImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * Test class for the <code>Manager</code>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class ManagerTest
{
    
    Manager<MockManagerChildImpl> _manager;
    
    @Before
    public void setup()
    {
        this._manager = new Manager<MockManagerChildImpl>();
    }
    
    @Test
    public void testCreateChildWithClassKey()
    {
        Assert.assertTrue(this._manager != null);
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        try
        {
            final Manageable child = this._manager
                    .create(MockManagerChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
            
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithClassNameKey()
    {
        Assert.assertTrue(this._manager != null);
        
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        try
        {
            final Manageable child = this._manager.create(className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClass()
    {
        Assert.assertTrue(this._manager != null);
        
        final String key = "test-mock-o";
        
        try
        {
            final Manageable child = this._manager.create(key,
                    MockManagerChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClassName()
    {
        Assert.assertTrue(this._manager != null);
        
        final String key = "test-mock-o";
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        try
        {
            final Manageable child = this._manager.create(key, className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testManagerCreation()
    {
        Assert.assertTrue(this._manager != null);
    }
    
    @Test
    public void testGetChildWithClassNameKey()
    {
        
        Assert.assertTrue(this._manager != null);
        
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        try
        {
            this._manager.create(className);
            
            final String key = MockManagerChildImpl.class.getName();
            final Manageable child = this._manager.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockManagerChildImpl);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testGetChildWithKey()
    {
        
        Assert.assertTrue(this._manager != null);
        
        final String key = "test-mock-o";
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        try
        {
            this._manager.create(key, className);
            
            final Manageable child = this._manager.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockManagerChildImpl);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testManagerClose()
    {
        this._manager.close();
        
        try
        {
            final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
            boolean isFound = this._manager.containsChild(className);
            System.out.println("isFound=" + isFound);
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
            MockManagerChildImpl child = this._manager.create(className,
                    className);
            System.out.println("Child=" + child);
            Assert.fail();
        }
        catch (ManageableException e)
        {
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
            MockManagerChildImpl child = this._manager.create(className,
                    MockManagerChildImpl.class);
            System.out.println("Child=" + child);
            Assert.fail();
        }
        catch (ManageableException e)
        {
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            MockManagerChildImpl child = this._manager
                    .create(MockManagerChildImpl.class);
            System.out.println("Child=" + child);
            Assert.fail();
        }
        catch (ManageableException e)
        {
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
            this._manager.closeChild(className);
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
            this._manager.get(className);
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            this._manager.getChildCount();
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        // test getKeys()
        try
        {
            this._manager.getKeys();
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
        
        // test isClose().
        boolean isClosed = this._manager.isClosed();
        Assert.assertTrue(isClosed);
        
        
        // test repeated close();
        try
        {
            this._manager.close();
            Assert.fail();
        }
        catch (ManagerClosedException e)
        {
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void testClose2()
    {
        Assert.assertTrue(this._manager != null);
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        try
        {
            Manageable child = this._manager
                    .create("c1", MockManagerChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue("c1".equals(child.getKey()));
            
            child = this._manager
                    .create("c2", MockManagerChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue("c2".equals(child.getKey()));
            
            child = this._manager
                    .create("c3", MockManagerChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue("c3".equals(child.getKey()));
            
            int count = this._manager.getChildCount();
            Assert.assertTrue(count == 3);
            
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
        catch (final Exception e)
        {
        	e.printStackTrace();
            Assert.fail(e.toString());
        }
        
        try
        {
            this._manager.close();
            Assert.assertTrue(this._manager.isClosed());
        }
        catch (final ManagerClosedException e)
        {
        	e.printStackTrace();
            Assert.fail(e.toString());
        }
        catch (final Exception e)
        {
        	e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
