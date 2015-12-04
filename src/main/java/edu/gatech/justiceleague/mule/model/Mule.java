package edu.gatech.justiceleague.mule.model;

/**
 * Generic interface for a Mule
 */
public interface Mule {

    /**
     * Calculates and sets production for mule
     * depending on terrain and mule type
     */
    void calculateProduction();

    /**
     * Return a string describing the Mule type
     * @return Mule type string
     */
     String getType();

}
