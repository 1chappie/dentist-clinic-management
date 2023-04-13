package domain;

public interface Identifiable<ID>{

    public void setID(ID id);

    public ID getID();

    @Override
    public boolean equals(Object o);
}
