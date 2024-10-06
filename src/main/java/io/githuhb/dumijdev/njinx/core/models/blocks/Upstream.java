package io.githuhb.dumijdev.njinx.core.models.blocks;

import io.githuhb.dumijdev.njinx.core.models.Block;
import io.githuhb.dumijdev.njinx.core.models.Directive;
import io.githuhb.dumijdev.njinx.core.models.Param;

import java.util.LinkedList;
import java.util.List;

public class Upstream implements Block {
    private final List<Param> params = new LinkedList<>();
    private final String upstreamName;

    public Upstream(String name) {
        this.upstreamName = name;
    }

    @Override
    public String name() {
        return "upstream";
    }

    @Override
    public Upstream add(Directive directive) {
        if (directive instanceof Param) {
            params.add((Param) directive);
        } else {
            throw new IllegalArgumentException("Only Param is allowed in Upstream.");
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
        return upstreamName;
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
