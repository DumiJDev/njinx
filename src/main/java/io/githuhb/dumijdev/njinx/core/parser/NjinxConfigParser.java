package io.githuhb.dumijdev.njinx.core.parser;

import io.githuhb.dumijdev.njinx.core.models.Block;
import io.githuhb.dumijdev.njinx.core.models.Param;
import io.githuhb.dumijdev.njinx.core.models.blocks.*;
import io.githuhb.dumijdev.njinx.core.models.params.SimpleParam;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Stack;

public class NjinxConfigParser {
    private final ThreadLocal<Stack<Block>> blockStackThreadLocal = ThreadLocal.withInitial(Stack::new);
    public NjinxConfig read(File file) throws IOException {
        try (var reader = new BufferedReader(new FileReader(file))) {
            return parseConfig(reader);
        }
    }

    public NjinxConfig read(String string) throws IOException {
        try (var reader = new BufferedReader(new StringReader(string))) {
            return parseConfig(reader);
        }
    }

    public NjinxConfig read(InputStream inputStream) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return parseConfig(reader);
        }
    }


    private NjinxConfig parseConfig(BufferedReader reader) throws IOException {
        var config = new NjinxConfig();
        var blockStack = blockStackThreadLocal.get();
        blockStack.push(config);

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            if (line.endsWith("{")) {
                line = line.substring(0, line.length() - 1).trim();
                String[] parts = line.split("\\s+");

                String blockName = parts[0];
                String args = parts.length > 1 ? line.substring(blockName.length()).trim() : null;

                Block newBlock = createBlock(blockName, args);
                blockStack.peek().add(newBlock);
                blockStack.push(newBlock);
            }
            else if (line.equals("}")) {
                blockStack.pop();
            }
            else {
                StringBuilder paramBuilder = new StringBuilder(line);

                while (!paramBuilder.toString().trim().endsWith(";")) {
                    String nextLine = reader.readLine();
                    if (nextLine == null) {
                        throw new IllegalArgumentException("Parameter not terminated with a semicolon.");
                    }
                    paramBuilder.append(" ").append(nextLine.trim());
                }

                String fullParamLine = paramBuilder.toString().trim();
                fullParamLine = fullParamLine.substring(0, fullParamLine.length() - 1).trim();

                String[] parts = fullParamLine.split("\\s+");
                String paramName = parts[0];
                String paramValue = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));

                Param param = new SimpleParam(paramName, paramValue);
                blockStack.peek().add(param);
            }
        }

        return config;
    }

    public void write(NjinxConfig config, File file) throws IOException {
        write(config, file.toPath());
    }

    public void write(NjinxConfig config, OutputStream outputStream) throws IOException {
        outputStream.write(config.generate().getBytes());
    }

    private void write(NjinxConfig config, Path filePath) throws IOException {
        String configContent = config.generate();
        Files.deleteIfExists(filePath);
        Files.writeString(filePath, configContent, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    public void write(NjinxConfig config, String fileName) throws IOException {
        Path filePath = Path.of(fileName);
        write(config, filePath);
    }

    private Block createBlock(String blockName, String args) {
        switch (blockName) {
            case "http":
                return new Http();
            case "events":
                return new Events();
            case "server":
                return new Server();
            case "location":
                if (args == null || args.isEmpty()) {
                    throw new IllegalArgumentException("Location block requires a path NginxDirectives.java an argument.");
                }
                return new Location(args);
            case "upstream":
                if (args == null) {
                    throw new IllegalArgumentException("Upstream block requires a name NginxDirectives.java an argument.");
                }
                return new Upstream(args);
            default:
                throw new IllegalArgumentException("Unknown block: " + blockName);
        }
    }
}
