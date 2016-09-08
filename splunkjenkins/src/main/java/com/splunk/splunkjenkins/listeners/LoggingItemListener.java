package com.splunk.splunkjenkins.listeners;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.listeners.ItemListener;

import static com.splunk.splunkjenkins.utils.LogEventHelper.getUserName;
import static com.splunk.splunkjenkins.utils.LogEventHelper.logUserAction;

@Extension
public class LoggingItemListener extends ItemListener {
    @Override
    public void onCreated(Item item) {
        logUserAction(getUserName(), Messages.audit_create_item(item.getUrl()));
    }

    @Override
    public void onCopied(Item src, Item item) {
        logUserAction(getUserName(), Messages.audit_cloned_item(item.getUrl(), src.getUrl()));
    }

    @Override
    public void onDeleted(Item item) {
        logUserAction(getUserName(), Messages.audit_delete_item(item.getUrl()));
    }

    @Override
    public void onRenamed(Item item, String oldName, String newName) {
        //no-op, we use onLocationChanged
    }

    @Override
    public void onUpdated(Item item) {
        //prior to delete, makeDisabled was called and onUpdated is triggered
        logUserAction(getUserName(), Messages.audit_update_item(item.getUrl()));
    }

    @Override
    public void onLocationChanged(Item item, String oldFullName, String newFullName) {
        logUserAction(getUserName(), Messages.audit_rename_item(item.getUrl(), oldFullName, newFullName));
    }
}
