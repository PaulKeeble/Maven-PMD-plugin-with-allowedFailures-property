package org.apache.maven.plugin.pmd;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.xml.pull.XmlPullParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * Fail the build if there were any PMD violations in the source code.
 *
 * @since 2.0
 * @version $Id: PmdViolationCheckMojo.java 939751 2010-04-30 17:36:29Z hboutemy $
 * @goal check
 * @phase verify
 * @execute goal="pmd"
 */
public class PmdViolationCheckMojo
    extends AbstractPmdViolationCheckMojo
{
    /**
     * What priority level to fail the build on. Failures at or above this level
     * will stop the build. Anything below will be warnings and will be
     * displayed in the build output if verbose=true. Note: Minimum Priority = 5
     * Maximum Priority = 0
     *
     * @parameter expression="${pmd.failurePriority}" default-value="5"
     * @required
     */
    private int failurePriority;

    /**
     * Skip the PMD checks.  Most useful on the command line
     * via "-Dpmd.skip=true".
     *
     * @parameter expression="${pmd.skip}" default-value="false"
     */
    private boolean skip;

    /** {@inheritDoc} */
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        if ( !skip )
        {
            executeCheck( "pmd.xml", "violation", "PMD violation", failurePriority );
        }
    }

    /** {@inheritDoc} */
    protected void printError( Map item, String severity )
    {

        StringBuffer buff = new StringBuffer( 100 );
        buff.append( "PMD " + severity + ": " );
        if ( item.containsKey( "class" ) )
        {
            if ( item.containsKey( "package" ) )
            {
                buff.append( item.get( "package" ) );
                buff.append( "." );
            }
            buff.append( item.get( "class" ) );
        }
        else
        {
            buff.append( item.get( "filename" ) );
        }
        buff.append( ":" );
        buff.append( item.get( "beginline" ) );
        buff.append( " Rule:" ).append( item.get( "rule" ) );
        buff.append( " Priority:" ).append( item.get( "priority" ) );
        buff.append( " " ).append( item.get( "text" ) ).append( "." );

        this.getLog().info( buff.toString() );
    }

    /** {@inheritDoc} */
    protected Map getErrorDetails( XmlPullParser xpp )
        throws XmlPullParserException, IOException
    {
        int index = 0;
        int attributeCount = 0;
        HashMap msgs = new HashMap();

        attributeCount = xpp.getAttributeCount();
        while ( index < attributeCount )
        {

            msgs.put( xpp.getAttributeName( index ), xpp.getAttributeValue( index ) );

            index++;
        }

        // get the tag's text
        if ( xpp.next() == XmlPullParser.TEXT )
        {
            msgs.put( "text", xpp.getText().trim() );
        }
        return msgs;
    }
}
