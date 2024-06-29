package nz.ac.auckland.se281;

/** The exception that is thrown if the inputted country name is invalid. */
public class InvalidCountryNameException extends Exception {

  /**
   * The exception is thrown if no country is found with the input, it is a checked exception.
   *
   * @param name The user input.
   */
  public InvalidCountryNameException(String name) {
    super(MessageCli.INVALID_COUNTRY.getMessage(name));
  }
}
