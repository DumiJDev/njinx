package io.githuhb.dumijdev.njinx.core.models.blocks;

import io.githuhb.dumijdev.njinx.core.models.Block;
import io.githuhb.dumijdev.njinx.core.models.Directive;
import io.githuhb.dumijdev.njinx.core.models.Param;

import java.util.LinkedList;
import java.util.List;

public class Location implements Block {
    private final List<Param> params = new LinkedList<>();
    private final String locationPath;

    public Location(String path) {
        this.locationPath = path;
    }

    @Override
    public String name() {
        return "location";
    }

    @Override
    public Location add(Directive directive) {
        if (directive instanceof Param) {
            params.add((Param) directive);
        } else {
            throw new IllegalArgumentException("Only Param is allowed in Location.");
        }
        return this;
    }

    @Override
    public void remove(Directive directive) {
        if (directive instanceof Param) {
            params.remove(directive);
        }
    }

    @Override
    public Iterable<Block> blocks() {
        return List.of();
    }

    @Override
    public String args() {
        return locationPath;
    }

    @Override
    public Iterable<Param> params() {
        return params;
    }

    @Override
    public String toString() {
        return generate();
    }
}
