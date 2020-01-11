/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Annika
 */
public class JokeDTO {
    private List<Map<String, String>> jokes;
    private String reference;
    
    public JokeDTO() {}
    
    public JokeDTO(List<Map<String, String>> jokes, String reference) {
        this.jokes = jokes;
        this.reference = reference;
    }

    public List<Map<String, String>> getJokes() {
        return jokes;
    }

    public String getReference() {
        return reference;
    }

    public void setJokes(List<Map<String, String>> jokes) {
        this.jokes = jokes;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
