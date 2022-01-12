package model;

public class NamedEntity extends BaseEntity {

    private String name;

    public String getName() {
        return this.name;
    }

    public NamedEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
