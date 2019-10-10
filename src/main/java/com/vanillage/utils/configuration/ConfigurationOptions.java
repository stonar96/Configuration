package com.vanillage.utils.configuration;

public final class ConfigurationOptions {
    private boolean defaultConfigurationAddedOnSave = true;
    private boolean nullTreatedAsValue = false; // nullAValue, !nullNotAValue, !nullNoValue

    public boolean isDefaultConfigurationAddedOnSave() {
        return defaultConfigurationAddedOnSave;
    }

    public void setDefaultConfigurationAddedOnSave(boolean defaultConfigurationAddedOnSave) {
        this.defaultConfigurationAddedOnSave = defaultConfigurationAddedOnSave;
    }

    public boolean isNullTreatedAsValue() {
        return nullTreatedAsValue;
    }

    public void setNullTreatedAsValue(boolean nullTreatedAsValue) {
        this.nullTreatedAsValue = nullTreatedAsValue;
    }
}
