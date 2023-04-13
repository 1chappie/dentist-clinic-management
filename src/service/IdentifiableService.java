package service;

import domain.Identifiable;
import exception.RepoException;
import repository.IRepository;

import java.util.ArrayList;
import java.util.Map;

abstract public class IdentifiableService<T extends Identifiable<String>> {

    private IRepository<String,T> link;

    public IdentifiableService(IRepository<String,T> link) {
        this.link = link;
    }

    @SafeVarargs
    public final void add(Map.Entry<String, T>... mapEntries) throws RepoException {
        for (Map.Entry<String, T> m : mapEntries)
            if (!link.add(m.getKey(), m.getValue()))
                throw new RepoException("Entity already exists");
    }

    @SafeVarargs
    public final void add(T... entities) throws RepoException {
        for (T e : entities)
            if (!link.add(e.getID(), e))
                throw new RepoException("Entity already exists");
    }

    @SafeVarargs
    public final void remove(Map.Entry<String, T>... mapEntries) throws RepoException {
        for (Map.Entry<String, T> m : mapEntries)
            if (!link.remove(m.getKey()))
                throw new RepoException("Entity already exists");
    }

    @SafeVarargs
    public final void remove(T... entities) throws RepoException {
        for (T e : entities)
            if (!link.remove(e.getID()))
                throw new RepoException("Entity does not exist");
    }

    public void update(Map.Entry<String, T> mapEntry) throws RepoException {
        if (!link.update(mapEntry.getKey(), mapEntry.getValue()))
            throw new RepoException("Entity does not exist");
    }

    public void update(T entity) throws RepoException {
        if (!link.update(entity.getID(), entity))
            throw new RepoException("Entity does not exist");
    }

    public T get(String id) throws RepoException {
        T e = link.findById(id);
        if (e == null)
            throw new RepoException("Entity does not exist");
        return e;
    }

    public ArrayList<T> get() {
        return (ArrayList<T>) link.getAll();
    }

    public boolean contains(String id) {
        return link.findById(id) != null;
    }

    public Integer size() {
        return link.size();
    }

    @Override
    public String toString() {
        String s = "";
        for (T e : link.getAll())
            s += e.toString() + "\n";
        return s;
    }
}
