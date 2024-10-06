package io.githuhb.dumijdev.njinx.core.models.params;

import io.githuhb.dumijdev.njinx.core.models.Param;

public class SimpleParam implements Param {
    private final String name;
    private final String value;

    public SimpleParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return generate();
    }
}
