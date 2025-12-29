package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc {
  @Override
  public boolean matchJpeg(String filename) {
    if (filename == null) {
      return false;
    }
    return filename.matches("(?i).*\\.(jpg|jpeg)$");
  }

  @Override
  public boolean matchIP(String ip) {
    if (ip == null) {
      return false;
    }
    return ip.matches("^\\d{1,3}(\\.\\d{1,3}){3}$");
  }

  @Override
  public boolean isEmptyLine(String line) {
    if (line == null) {
      return false;
    }
    return line.matches("^\\s*$");
  }
}
