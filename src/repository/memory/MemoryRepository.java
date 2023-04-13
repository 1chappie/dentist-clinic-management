package repository.memory;

import repository.IRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MemoryRepository<ID, T> implements IRepository<ID, T> {
    public Map<ID, T> repo;

    public MemoryRepository() {
        this.repo = new HashMap<ID, T>();
    }

    @Override
    public boolean add(ID id, T entity) {
        return repo.put(id, entity) == null;
    }

    @Override
    public boolean remove(ID id) {
        return repo.remove(id) != null;
    }

    @Override
    public boolean update(ID id, T entity) {
        return repo.replace(id, entity) != null;

    }

    @Override
    public T findById(ID id) {
        return repo.get(id);
    }

    // Copy of the values of the map
    @Override
    public List<T> getAll() {
        return new ArrayList<T>(repo.values());
    }

    @Override
    public Integer size() {
        return repo.size();
    }

}
