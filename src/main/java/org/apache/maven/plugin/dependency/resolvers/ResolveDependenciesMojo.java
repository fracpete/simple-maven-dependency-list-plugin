package org.apache.maven.plugin.dependency.resolvers;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.dependency.utils.DependencyStatusSets;
import org.apache.maven.plugin.dependency.utils.DependencyUtil;
import org.apache.maven.plugin.dependency.utils.filters.ResolveFileFilter;
import org.apache.maven.plugin.dependency.utils.markers.SourcesFileMarkerHandler;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.artifact.filter.collection.ArtifactsFilter;

import java.io.IOException;

/**
 * Goal that resolves the project dependencies from the repository.
 *
 * @author <a href="mailto:brianf@apache.org">Brian Fox</a>
 * @version $Id: ResolveDependenciesMojo.java 1482616 2013-05-14 22:08:15Z hboutemy $
 * @since 2.0
 */
public class ResolveDependenciesMojo
    extends AbstractResolveMojo
{

    /**
     * If we should display the scope when resolving
     *
     * @since 0.0.1
     */
    @Parameter( property = "mdep.outputScope", defaultValue = "true" )
    protected boolean outputScope;

    /**
     * If we should display the classifier when resolving
     *
     * @since 0.0.1
     */
    @Parameter( property = "mdep.outputClassifier", defaultValue = "true" )
    protected boolean outputClassifier;

    /**
     * If we should display the version when resolving
     *
     * @since 0.0.1
     */
    @Parameter( property = "mdep.outputVersion", defaultValue = "true" )
    protected boolean outputVersion;

    /**
     * If we should display the group ID when resolving
     *
     * @since 0.0.1
     */
    @Parameter( property = "mdep.outputGroupId", defaultValue = "true" )
    protected boolean outputGroupId;

    /**
     * Only used to store results for integration test validation
     */
    DependencyStatusSets results;

    /**
     * Sort the output list of resolved artifacts alphabetically.
     * The default ordering matches the classpath order.
     * 
     * @since 0.0.1
     */
    @Parameter( property = "sort", defaultValue = "false" )
    boolean sort;

    /**
     * Include parent poms in the dependency resolution list.
     * 
     * @since 0.0.1
     */
    @Parameter( property = "includeParents", defaultValue = "false" )
    boolean includeParents;

    /**
     * Main entry into mojo. Gets the list of dependencies and iterates through displaying the resolved version.
     *
     * @throws MojoExecutionException with a message if an error occurs.
     */
    protected void doExecute()
        throws MojoExecutionException
    {
        // get sets of dependencies
        results = this.getDependencySets( false, includeParents );

        String output = results.getOutput(
          outputAbsoluteArtifactFilename, outputScope, outputClassifier,
          outputVersion, outputGroupId, sort );

        try
        {
            if ( outputFile == null )
            {
                DependencyUtil.log( output, getLog() );
            }
            else
            {
                DependencyUtil.write( output, outputFile, appendOutput, getLog() );
            }
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( e.getMessage(), e );
        }
    }

    /**
     * @return Returns the results.
     */
    public DependencyStatusSets getResults()
    {
        return this.results;
    }

    protected ArtifactsFilter getMarkedArtifactFilter()
    {
        return new ResolveFileFilter( new SourcesFileMarkerHandler( this.markersDirectory ) );
    }
}
