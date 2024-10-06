package io.githuhb.dumijdev.njinx.core.models.blocks;

import io.githuhb.dumijdev.njinx.core.models.Block;
import io.githuhb.dumijdev.njinx.core.models.Directive;
import io.githuhb.dumijdev.njinx.core.models.Param;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Server implements Block {
    private final List<Block> locations = new LinkedList<>();
    private final List<Param> params = new LinkedList<>();

    @Override
    public String name() {
        return "server";
    }

    @Override
    public Server add(Directive directive) {
        if (directive instanceof Location) {
            locations.add((Block) directive);
        } else if (directive instanceof Param) {
            params.add((Param) directive);
        } else {
            throw new IllegalArgumentException("Invalid directive type. Only Location Block or Param are allowed.");
        }
        return this;
    }

    @Override
    public void remove(Directive directive) {
        if (directive instanceof Block) {
            locations.remove(directive);
        } else if (directive instanceof Param) {
            params.remove(directive);
        }
    }

    @Override
    public Iterable<Block> blocks() {
        return locations;
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

    public Iterable<Location> locations() {
        return locations.stream()
                .filter(block -> block instanceof Location)
                .map(block -> (Location) block)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
