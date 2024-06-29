package nz.ac.auckland.se281;

/** The country object has a name, continent and tax for crossing into. */
public class Country {
  private String name;
  private String continent;
  private int crossBorderTax;

  /**
   * This is a constructor for the country object, and creates a new country instance.
   *
   * @param name Name of specific country.
   * @param continent Continent of specific country.
   * @param crossBorderTax Tax that comes when crossing that country.
   */
  public Country(String name, String continent, int crossBorderTax) {
    this.name = name;
    this.continent = continent;
    this.crossBorderTax = crossBorderTax;
  }

  public String getName() {
    return this.name;
  }

  public String getContinent() {
    return this.continent;
  }

  public int getCrossBorderTax() {
    return this.crossBorderTax;
  }

  @Override
  public boolean equals(Object obj) {
    // If both variables reference the exact same destination
    if (this == obj) return true;
    // if input is null
    if (obj == null) return false;
    // if both are of same instance
    if (getClass() != obj.getClass()) return false;
    Country other = (Country) obj;
    // if country name is null
    if (name == null) {
      // if input country name isn't null
      if (other.name != null) return false;
    }
    // if input country name doesn't equal country name
    else if (!name.equals(other.name)) return false;
    // if country continent is null
    if (continent == null) {
      // if input country continent isn't null
      if (other.continent != null) return false;
      // if input country continent does not equal country continent
    } else if (!continent.equals(other.continent)) return false;
    // if input country tax does not equal country tax
    if (crossBorderTax != other.crossBorderTax) return false;
    return true;
  }

  @Override
  public int hashCode() {
    // Define a prime number to use in the hash code calculation.
    final int prime = 31;
    // Initialize the result with a non-zero value.
    int result = 1;
    // Calculate the hash code for the 'name' field and add it to the result.
    // If 'name' is null, use 0; otherwise, use 'name.hashCode()'.
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    // Calculate the hash code for the 'continent' field and add it to the result.
    // If 'continent' is null, use 0; otherwise, use 'continent.hashCode()'.
    result = prime * result + ((continent == null) ? 0 : continent.hashCode());
    // Add the 'crossBorderTax' field value to the result.
    result = prime * result + crossBorderTax;
    // Return the computed hash code.
    return result;
  }
}
