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

package org.plukh.fluffymeow.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class S3Properties extends Properties {
    private static final Logger log = LogManager.getLogger(S3Properties.class);

    public static final String DEFAULT_BUCKET = "signal-config";
    public static final String DEFAULT_PROPERTIES = "default.properties";

    protected transient final AmazonS3 amazonS3;
    protected transient final VersionInfoProvider versionInfoProvider;

    protected final String bucket;

    public S3Properties() {
        amazonS3 = new AmazonS3Client();
        versionInfoProvider = VersionInfoProviderFactory.getInstance();
        bucket = DEFAULT_BUCKET;
    }

    public S3Properties(Properties defaults) {
        super(defaults);
        amazonS3 = new AmazonS3Client();
        versionInfoProvider = VersionInfoProviderFactory.getInstance();
        bucket = DEFAULT_BUCKET;
    }

    public S3Properties(AmazonS3 amazonS3, VersionInfoProvider versionInfoProvider, String bucket) {
        this.amazonS3 = amazonS3;
        this.versionInfoProvider = versionInfoProvider;
        this.bucket = bucket;
    }

    public S3Properties(Properties defaults, AmazonS3 amazonS3, VersionInfoProvider versionInfoProvider, String bucket) {
        super(defaults);
        this.amazonS3 = amazonS3;
        this.versionInfoProvider = versionInfoProvider;
        this.bucket = bucket;
    }

    public synchronized void load(String deploymentId, String applicationName, String configFileName) throws IOException {
        BufferedInputStream in = null;
        if (StringUtils.isEmpty(configFileName)) configFileName = applicationName + ".properties";

        try {
            in = new BufferedInputStream(new FileInputStream(configFileName));
        } catch (FileNotFoundException e) {
            log.debug("Local config file " + configFileName + " not found");
        }

        load(deploymentId, applicationName, in);
    }

    public synchronized void load(String deploymentId, String applicationName) throws IOException {
        load(deploymentId, applicationName, applicationName + ".properties");
    }

    public synchronized void load(String deploymentId, String applicationName, InputStream in) throws IOException {
        if (in != null) {
            try {
                log.debug("Trying to load properties from provided input stream");
                super.load(in);
                if (log.isDebugEnabled()) log.debug("Loaded " + size() + " total properties");
                return;
            } catch (IOException e) {
                log.debug("Failed to load properties from provided input stream", e);
            }
        }

        log.debug("Proceeding with S3 download");

        log.trace("Downloading default properties");
        Properties defaultProperties = downloadFromS3(bucket, DEFAULT_PROPERTIES, null);

        log.trace("Downloading deployment-default properties");
        Properties deplDefaultProperties =
                downloadFromS3(bucket, deploymentId + "/" + DEFAULT_PROPERTIES, defaultProperties);

        log.trace("Downloading application-specific properties");
        Properties appProperties =
                downloadFromS3(bucket, deploymentId + "/" + applicationName + "/" + applicationName + ".properties",
                        deplDefaultProperties);

        log.trace("Downloading version-specific properties");

        Properties versionProperties = downloadFromS3(bucket, deploymentId + "/" + applicationName + "/" +
                applicationName + "-" + versionInfoProvider.getVersion() + ".properties", appProperties);

        //Validate properties (at least one specific property file should be found!)
        if (appProperties.isEmpty() && versionProperties.isEmpty())
            throw new ConfigException("No specific configuration files found!");

        clear();
        Enumeration<?> names = versionProperties.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            this.setProperty(name, versionProperties.getProperty(name));
        }

        if (log.isDebugEnabled()) log.debug("Loaded " + size() + " total properties");
    }

    private Properties downloadFromS3(String bucket, String key, Properties defaultProperties) throws IOException {
        Properties properties = new Properties(defaultProperties);

        try (S3Object object = amazonS3.getObject(bucket, key)) {
            try (InputStream in = object.getObjectContent()) {
                if (log.isTraceEnabled()) log.trace("Downloading from " + bucket + ":" + key + ", size: " +
                        object.getObjectMetadata().getInstanceLength());
                properties.load(in);
                if (log.isTraceEnabled()) log.trace("Downloaded " + properties.size() + " properties");
            }
        } catch (AmazonS3Exception e) {
            if (isObjectNotFound(e)) {
                //Not found is ok, just means the file is not present
                if (log.isDebugEnabled()) log.debug("Object " + bucket + ":" + key + " not found");
            } else {
                //Some other exception
                throw e;
            }
        }

        return properties;
    }

    private boolean isObjectNotFound(AmazonS3Exception e) {
        return e.getStatusCode() == 404;
    }

    @Override
    public synchronized void load(Reader reader) throws IOException {
        throw new NotSupportedException("This method is not supported for S3 properties");
    }

    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        throw new NotSupportedException("This method is not supported for S3 properties");
    }

    @Override
    public void store(Writer writer, String comments) throws IOException {
        throw new NotSupportedException("This method is not supported for S3 properties");
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        throw new NotSupportedException("This method is not supported for S3 properties");
    }

    @Override
    public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
        throw new NotSupportedException("This method is not supported for S3 properties");
    }

    @Override
    public void storeToXML(OutputStream os, String comment) throws IOException {
        throw new NotSupportedException("This method is not supported for S3 properties");
    }

    @Override
    public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
        throw new NotSupportedException("This method is not supported for S3 properties");
    }
}
