package io.githuhb.dumijdev.njinx.core.models.blocks;

import io.githuhb.dumijdev.njinx.core.models.Block;
import io.githuhb.dumijdev.njinx.core.models.Directive;
import io.githuhb.dumijdev.njinx.core.models.Param;

import java.util.LinkedList;
import java.util.List;

public class NjinxConfig implements Block {
    private final List<Block> topLevelBlocks = new LinkedList<>();
    private final List<Param> globalParams = new LinkedList<>();

    @Override
    public NjinxConfig add(Directive directive) {
        if (directive instanceof Block) {
            topLevelBlocks.add((Block) directive);
        } else if (directive instanceof Param) {
            globalParams.add((Param) directive);
        } else {
            throw new IllegalArgumentException("Invalid directive type: " + directive.getClass().getSimpleName());
        }
        return this;
    }

    @Override
    public void remove(Directive directive) {
        if (directive instanceof Block) {
            topLevelBlocks.remove(directive);
        } else if (directive instanceof Param) {
            globalParams.remove(directive);
        }
    }

    @Override
    public Iterable<Block> blocks() {
        return topLevelBlocks;
    }

    @Override
    public Iterable<Param> params() {
        return globalParams;
    }

    public Http http() {
        for (var block : findBlockByName("http")) {
            if (block instanceof Http) {
                return (Http) block;
            }
        }

        return null;
    }

    public Events events() {
        for (var block : findBlockByName("events")) {
            if (block instanceof Events) {
                return (Events) block;
            }
        }

        return null;
    }

    @Override
    public String args() {
        return "";
    }

    @Override
    public String name() {
        return "nginx";
    }

    @Override
    public String toString() {
        return generate();
    }
}
