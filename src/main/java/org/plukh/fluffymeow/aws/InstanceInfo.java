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

public class InstanceInfo {
    private String instanceId;
    private String name;
    private String deploymentId;

    public InstanceInfo() {
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InstanceInfo withInstanceId(final String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public InstanceInfo withName(final String name) {
        this.name = name;
        return this;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public InstanceInfo withDeploymentId(final String deploymentId) {
        this.deploymentId = deploymentId;
        return this;
    }

    @Override
    public String toString() {
        return "InstanceInfo{" +
                "instanceId='" + instanceId + '\'' +
                ", name='" + name + '\'' +
                ", deploymentId='" + deploymentId + '\'' +
                '}';
    }
}
