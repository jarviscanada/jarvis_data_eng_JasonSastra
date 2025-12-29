package ca.jrvs.apps.grep;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep  {

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public void process() throws IOException {
    List<File> listFiles = this.listFiles(this.getRootPath());
    List<String> finalLines = new ArrayList<>();
    String regex = this.getRegex();
    Pattern pattern = Pattern.compile(regex);
    for (File file : listFiles) {
      List<String> listStrings = this.readLines(file);

      int lineNum = 1;
      for (String line : listStrings) {

        if (pattern.matcher(line).find()) {
          finalLines.add(file.getPath() + ":" + lineNum + ":" + line);
        }
        lineNum++;
      }
    }

    this.writeToFile(finalLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File directory = new File(rootPath);
    List<File> files = new ArrayList<>();

    if (!directory.exists() || !directory.isDirectory()) {
      // Check if Directory is actually a directory and exist
      return files;
    }

    File[] listOfFiles = directory.listFiles();
    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        if (file.isFile()) {
          files.add(file);
        }
      }
    }

    return files;

  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(
          "Failed to read file: " + inputFile.getAbsolutePath(), e
      );
    }

    return lines;
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
      try (FileOutputStream fileOutputStream = new FileOutputStream(this.getOutFile())) {

        for (String line : lines ) {
          fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
          fileOutputStream.write(System.lineSeparator()
              .getBytes(StandardCharsets.UTF_8));
        }
      } catch (IOException e) {
        throw new IOException(
            "Failed to write File: " + this.getOutFile(), e
        );
      }
  }

  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return this.regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return this.outFile;
  }

  @Override
  public void SetOutFile(String outFile) {
    this.outFile = outFile;
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException(("USAGE: JavaGrep regex rootpath outfile"));
    }

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.SetOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (IOException e) {
      throw new RuntimeException(
          "Failed to process request", e
      );
    }
  }
}
