package io.githuhb.dumijdev.njinx.core.models;

import java.util.LinkedList;

import static java.lang.String.format;

public interface Block extends Directive {
    Block add(Directive directive);

    void remove(Directive directive);

    Iterable<Block> blocks();

    String args();

    Iterable<Param> params();

    default Iterable<Block> findBlockByName(String name) {
        var objects = new LinkedList<Block>();
        for (Block block : blocks()) {
            if (name.equals(block.name())) {
                objects.add(block);
            }
        }

        return objects;
    }

    default Iterable<Param> findParamByName(String name) {
        var objects = new LinkedList<Param>();

        for (var param : params()) {
            if (name.equals(param.name())) {
                objects.add(param);
            }

        }
        return objects;
    }

    private String generate(int indent) {

        StringBuilder sb = new StringBuilder("nginx".equals(name()) ? "" : format("%s%s %s {\n", "".repeat(indent), name(), args()));
        if (params() != null) {

            for (Param param : params()) {
                sb.append(" ".repeat(indent)).append(param).append("\n");
            }
        }

        if (blocks() != null) {

            for (Block block : blocks()) {
                sb.append("\n")
                        .append(" ".repeat(indent))
                        .append(block.generate(indent + 4)).append("\n");
            }

            if (!"nginx".equals(name())) {
                sb.append(String.format("%s}", " ".repeat((indent > 4 ? indent - 4 : 0))));
            }
        }


        return sb.toString();
    }

    @Override
    default String generate() {
        return generate(0);
    }
}
