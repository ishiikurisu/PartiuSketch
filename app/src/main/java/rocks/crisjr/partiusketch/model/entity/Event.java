package rocks.crisjr.partiusketch.model.entity;

/**
 * Created by Cris Joe Jr. (cristianoalvesjr@gmail.com) on 28/12/2015.
 */
public class Event {
    private String name;
    private String description;
    private String category;
    private String local;

    public Event() {

    }

    /* Gets and Sets */
    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public String setDescription(String description) {
        this.description = description;
        return this.description;
    }

    public String setLocal(String local) {
        this.local = local;
        return this.local;
    }

    public String setCategory(String category) {
        this.category = category;
        return this.category;
    }
}
