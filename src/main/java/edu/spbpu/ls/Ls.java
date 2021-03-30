package edu.spbpu.ls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ls {

    /**
     * -l
     */
    public String longFormat(File file) {
        // последняя модификация
        SimpleDateFormat date = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        String LastMod = date.format(file.lastModified());
        //размер
        long size = file.length();
        //права на выполнение/чтение/запись в виде битовой маски XXX
        String RWX = "";
        if (file.canRead()) RWX += '1';
        else RWX += '0';
        if (file.canWrite()) RWX += '1';
        else RWX += '0';
        if (file.canExecute()) RWX += '1';
        else RWX += '0';
        //результат
        return " " + RWX + " " + LastMod + " " + size;
    }

    /**
     * -h
     */
    public String humanReadable(File file) {
        StringBuilder size = new StringBuilder();
        Formatter fmt = new Formatter(size);
        long l = file.length();
        if (l > 1024 * 1024 * 1024) {
            fmt.format("%.1f Gb", (float) l / (1024 * 1024 * 1024));
        } else if (l > 1024 * 1024) {
            fmt.format("%.1f Mb", (float) l / (1024 * 1024));
        } else if (l > 1024) {
            fmt.format("%.1f Kb", (float) l / 1024);
        } else {
            fmt.format("%.1f b", (float) l);
        }

        String rwx = "";
        if (file.canRead()) rwx += 'r';
        else rwx += '-';
        if (file.canWrite()) rwx += 'w';
        else rwx += '-';
        if (file.canExecute()) rwx += 'x';
        else rwx += '-';
        return " " + size.toString() + " " + rwx;
    }

    /**
     * - o
     */
    void output(String s, List<String> list) throws FileNotFoundException {
        PrintStream out;
        out = new PrintStream(new File(s));
        for (String i : list) {
            out.println(i);
        }
    }

    /**
     * Ls
     */
    private final List<File> files;
    Ls(File file) {
        files = new ArrayList<>();
        File[] list = file.listFiles();
        if (file.isDirectory()) {
            if (list == null) throw new AssertionError();
            Collections.addAll(this.files, list);

        } else if (file.isFile()) this.files.add(file);
        Collections.sort(files);
    }

    public List<String> ls(boolean l, boolean h, boolean r) {
        List<String> answer = new ArrayList<>();
        for (File file : files) {
            StringBuilder s = new StringBuilder();
            Formatter fmt = new Formatter(s);
            s.append(file.getName());
            if (l && h) {
                fmt.format(longFormat(file) + " " + humanReadable(file));
            } else if (l) {
                fmt.format(longFormat(file));
            } else if (h) {
                fmt.format(humanReadable(file));
            }
            answer.add(s.toString());
        }
        if (r) Collections.reverse(answer);
        return answer;
    }
}

