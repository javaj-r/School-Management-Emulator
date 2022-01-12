package model;

public class BaseEntity {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public BaseEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public boolean isNew() {
        return this.id == null;
    }
}
