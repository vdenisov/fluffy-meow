/*
 * Fluffy Meow - Torrent RSS generator for TV series
 * Copyright (C) 2015 Victor Denisov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.plukh.fluffymeow.aws;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeTagsRequest;
import com.amazonaws.services.ec2.model.DescribeTagsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.TagDescription;
import org.apache.http.client.fluent.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AWSInstanceInfoProviderImpl implements InstanceInfoProvider {
    private static final Logger log = LogManager.getLogger(AWSInstanceInfoProviderImpl.class);
    private static final String NAME_TAG = "Name";
    private static final String DEPLOYMENT_ID_TAG = "deploymentId";

    private InstanceInfo instanceInfo;

    public AWSInstanceInfoProviderImpl() {
    }

    @Override
    public InstanceInfo getInstanceInfo() {
        if (instanceInfo == null) {
            try {
                AmazonEC2 ec2 = new AmazonEC2Client();

                String instanceId = Request.Get("http://169.254.169.254/latest/meta-data/instance-id").execute().returnContent().asString();
                if (log.isDebugEnabled()) log.debug("Instance Id: " + instanceId);

                DescribeTagsRequest tagsRequest = new DescribeTagsRequest().withFilters(
                        new Filter().withName("resource-id").withValues(instanceId),
                        new Filter().withName("key").withValues(NAME_TAG, DEPLOYMENT_ID_TAG));

                DescribeTagsResult tagsResult = ec2.describeTags(tagsRequest);

                String name = getTag(tagsResult, NAME_TAG);
                if (log.isDebugEnabled()) log.debug("Instance name: " + name);

                String deploymentId = getTag(tagsResult, DEPLOYMENT_ID_TAG);
                if (log.isDebugEnabled()) log.debug("Deployment: " + deploymentId);

                instanceInfo = new InstanceInfo()
                        .withInstanceId(instanceId)
                        .withName(name)
                        .withDeploymentId(deploymentId);
            } catch (IOException e) {
                throw new AWSInstanceInfoException("Error retrieving AWS instance info", e);
            }
        }

        return instanceInfo;
    }

    private String getTag(DescribeTagsResult tagsResult, String tagName) {
        for (TagDescription tag : tagsResult.getTags()) {
            if (tag.getKey().equals(tagName)) return tag.getValue();
        }
        return null;
    }
}
