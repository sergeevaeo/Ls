package edu.spbpu.ls;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    @Option(name = "-l", usage = "longFormat")
    private String lg;

    @Option(name = "-h", usage = "human-readable")
    private String hn;

    @Option(name = "-r", usage = "reverse")
    private String rev;

    @Option(name = "-o", usage = "output")
    private String out;

    @Argument(required = true,usage = "Input file name")
    private String directory_or_file;

    @Argument(usage = "Output file name")
    private String outputFile;

    public static void main(String[] args) {
        new Main().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar ls.jar [-l] [-h] [-r] [-o outputFile] directory_or_file");
            parser.printUsage(System.err);
            return;
        }
        try {
            Ls res = new Ls(new File(directory_or_file));
            boolean l = false;
            boolean h = false;
            boolean r = false;
            if (!lg.isEmpty()) {
                l = true;
            }
            if (!hn.isEmpty()) {
                h = true;
            }
            if (!rev.isEmpty()) {
                r = true;
            }
            List<String> answer = res.ls(l, h, r);
            if (!out.isEmpty()) {
                String s = outputFile;
                res.output(s, answer);
            } else {
                System.out.println(answer);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}



