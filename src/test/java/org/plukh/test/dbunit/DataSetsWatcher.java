package org.plukh.test.dbunit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class DataSetsWatcher extends TestWatcher {
    private static final Logger log = LogManager.getLogger(DataSetsWatcher.class);

    private String[] resources;

    @Override
    protected void starting(Description description) {
        Set<String> rs = new LinkedHashSet<>();

        //Get class annotation (if any)
        DataSets dataSets = description.getTestClass().getAnnotation(DataSets.class);
        if (dataSets != null) {
            Collections.addAll(rs, dataSets.value());
        }

        //Get method annotation
        dataSets = description.getAnnotation(DataSets.class);
        if (dataSets != null) {
            if (dataSets.override()) rs.clear();
            Collections.addAll(rs, dataSets.value());
        }

        resources = rs.toArray(new String[rs.size()]);

        //Warn if no datasets found
        if (rs.isEmpty()) log.warn("No datasets found for " + description.getDisplayName());
    }

    public String[] getResources() {
        return resources;
    }
}
