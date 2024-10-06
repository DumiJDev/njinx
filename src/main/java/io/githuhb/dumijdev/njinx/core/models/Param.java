package io.githuhb.dumijdev.njinx.core.models;

import static java.lang.String.format;

public interface Param extends Directive{
    String value();

    @Override
    default String generate() {
        return format("%s\t%s;", name(), value());
    }
}
