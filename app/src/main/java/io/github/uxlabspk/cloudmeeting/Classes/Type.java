package io.github.uxlabspk.cloudmeeting.Classes;

import io.github.uxlabspk.cloudmeeting.R;

public enum Type {
    CONFIRM(R.drawable.ic_question),
    INFORMATION(R.drawable.ic_notification),
    ERROR(R.drawable.ic_error);

    private int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
