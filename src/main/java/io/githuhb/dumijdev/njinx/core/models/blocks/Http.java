package io.githuhb.dumijdev.njinx.core.models.blocks;

import io.githuhb.dumijdev.njinx.core.models.Block;
import io.githuhb.dumijdev.njinx.core.models.Directive;
import io.githuhb.dumijdev.njinx.core.models.Param;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Http implements Block {
    private final List<Block> servers = new LinkedList<>();
    private final List<Param> params = new LinkedList<>();

    @Override
    public String name() {
        return "http";
    }

    @Override
    public Http add(Directive directive) {
        if (directive instanceof Block) {
            if (directive instanceof Server) {
                servers.add((Server) directive);
            } else {
                throw new IllegalArgumentException("Only ServerBlock can be added to HttpBlock.");
            }
        } else if (directive instanceof Param) {
            params.add((Param) directive);
        } else {
            throw new IllegalArgumentException("Invalid directive type. Only Block or Param are allowed.");
        }
        return this;
    }

    @Override
    public void remove(Directive directive) {
        if (directive instanceof Block) {
            servers.remove(directive);
        } else if (directive instanceof Param) {
            params.remove(directive);
        }
    }

    @Override
    public Iterable<Block> blocks() {
        return servers;
    }

    @Override
    public String args() {
        return "";
    }

    @Override
    public Iterable<Param> params() {
        return params;
    }

    @Override
    public String toString() {
        return generate();
    }

    public Iterable<Server> servers() {
        return servers.stream().map(block -> (Server) block)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
