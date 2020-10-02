package com.lecg.blog.service.importer;

import com.lecg.blog.exceptions.ScheduledImportException;

public abstract class ScheduledImporter {

    public abstract void scheduleUpdate();
    public abstract boolean containsNewData();
    protected abstract void updateDatabase() throws ScheduledImportException;

    protected void sync() {
        try {
            if (containsNewData()) {
                updateDatabase();
            }
        } catch (ScheduledImportException e) { // Not ideal I know!
            // logger.error("some warning here");
        }
    }
}
