/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author sasav
 */
public class TextFromArea {
    public String name;
    public String body;

    public TextFromArea(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public TextFromArea() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    
}
