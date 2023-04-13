package repository.file;

import repository.memory.MemoryRepository;

public abstract class FileRepository<ID, T> extends MemoryRepository<ID, T> {
    protected String fileName;

    public FileRepository(String fileName) {
        this.fileName = fileName;
        readFromFile();
    }

    abstract protected void readFromFile();

    abstract protected void writeToFile();

    @Override
    public boolean add(ID id, T elem) {
        if(!super.add(id, elem))
            return false;
        writeToFile();
        return true;
    }

    @Override
    public boolean remove(ID id) {
        if(!super.remove(id))
            return false;
        writeToFile();
        return true;
    }

    @Override
    public boolean update(ID id, T elem) {
        if(!super.update(id, elem))
            return false;
        writeToFile();
        return true;
    }

}