package org.apache.maven.plugin.pmd.stubs;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.artifact.handler.DefaultArtifactHandler;

/**
 * @author <a href="mailto:oching@apache.org">Maria Odea Ching</a>
 * @version $Id: DefaultArtifactHandlerStub.java 803853 2009-08-13 12:07:41Z vsiveton $
 */
public class DefaultArtifactHandlerStub
    extends DefaultArtifactHandler
{
    private String language;

    /** {@inheritDoc} */
    public String getLanguage()
    {
        if ( language == null )
        {
            language = "java";
        }

        return language;
    }

    public void setLanguage( String language )
    {
        this.language = language;
    }
}