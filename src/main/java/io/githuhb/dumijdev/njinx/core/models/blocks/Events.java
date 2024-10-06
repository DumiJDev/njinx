package io.githuhb.dumijdev.njinx.core.models.blocks;

import io.githuhb.dumijdev.njinx.core.models.Block;
import io.githuhb.dumijdev.njinx.core.models.Directive;
import io.githuhb.dumijdev.njinx.core.models.Param;

import java.util.LinkedList;
import java.util.List;

public class Events implements Block {
    private final List<Param> params = new LinkedList<>();

    @Override
    public String name() {
        return "events";
    }

    @Override
    public Events add(Directive directive) {
        if (directive instanceof Param) {
            params.add((Param) directive);
        } else {
            throw new IllegalArgumentException("Only Param is allowed in Events.");
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
    public List<Block> blocks() {
        return List.of();
    }

    @Override
    public String args() {
        return "";
    }

    @Override
    public List<Param> params() {
        return params;
    }

    @Override
    public String toString() {
        return generate();
    }
}
