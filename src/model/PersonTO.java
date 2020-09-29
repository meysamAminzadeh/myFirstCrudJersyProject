package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by asus on 6/12/2020.
 */
@XmlRootElement
public class PersonTO {
    private int id;
    private String name;
    private String family;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return "PersonTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                '}';
    }
}
