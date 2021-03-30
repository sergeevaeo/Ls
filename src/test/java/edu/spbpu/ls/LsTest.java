package edu.spbpu.ls;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LsTest {
    Ls res = new Ls(new File("input"));
    List<String> files = new ArrayList<>();

    @Test
    public void ls() {
        //given
        files.add("File 1");
        files.add("File 2");

        //when
        List<String> actual = res.ls(false, false, false);

        //then
        assertEquals(files, actual);
    }

    @Test
    public void r() {
        //given
        files.add("File 1");
        files.add("File 2");
        Collections.reverse(files);

        //when
        List<String> actual = res.ls(false, false, true);

        //then
        assertEquals(files, actual);
    }

    @Test
    public void l() {
        //given
        files.add("File 1 110 25 03 2021 00:27:00 1992");
        files.add("File 2 110 25 03 2021 00:27:00 2011");

        //when
        List<String> actual = res.ls(true, false, false);

        //then
        assertEquals(files, actual);
    }

    @Test
    public void h() {
        //given
        files.add("File 1 1,9 Kb rw-");
        files.add("File 2 2,0 Kb rw-");

        //when
        List<String> actual = res.ls(false, true, false);

        //then
        assertEquals(files, actual);
    }

    @Test
    public void hl() {
        //given
        files.add("File 1 110 25 03 2021 00:27:00 1992  1,9 Kb rw-");
        files.add("File 2 110 25 03 2021 00:27:00 2011  2,0 Kb rw-");

        //when
        List<String> actual = res.ls(true, true, false);

        //then
        assertEquals(files, actual);
    }

    @Test
    public void output() throws IOException {
        //given
        res.output("output.txt", res.ls(false, false, false));
        FileReader fr = new FileReader("output.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        StringBuilder str = new StringBuilder();
        while ((line = br.readLine()) != null) {
            str.append(line).append(" ");
        }
        //when
        String actual = "File 1 File 2 ";

        //then
        assertEquals(str.toString(), actual);
    }
}

