package ca.jrvs.apps.practice;

public interface RegexExc {

  /**
   * Returns true if filename extension is jpg or jpeg (case insensitive)
   * @param filename
   * @return
   */
  public boolean matchJpeg(String filename);

  /**
   * Returns true if ip is valid IP, IP adress range is from 0.0.0.0 to 999.999.999.999
   * @param ip
   * @return
   */
  public boolean matchIP(String ip);

  /**
   * returns true if line is empty (empty, whitespace, tabs, etc...)
   * @param line
   * @return
   */
  public boolean isEmptyLine(String line);
}
