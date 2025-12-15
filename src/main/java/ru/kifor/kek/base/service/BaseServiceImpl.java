package ru.kifor.kek.base.service;

import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kifor.kek.base.model.*;
import ru.kifor.kek.base.repository.BaseRepository;
import ru.kifor.kek.base.repository.BaseRepositoryImpl;
import ru.kifor.kek.utils.NotImplementedException;

import java.util.List;

public abstract class BaseServiceImpl<
    E extends TableImpl<Record>,
    C extends BaseCreateModel,
    G extends BaseModel,
    U extends BaseUpdateModel,
    F extends BaseFilterModel,
    R extends BaseRepositoryImpl<E, C, G,U, F>
    > implements BaseService<C,G,U,F>
  {
    @Autowired
    protected R repos;

    @Override
    public G create(C createModel) {
      return repos.create(createModel);
    }

    @Override
    public G getById(Long id) {
      return repos.getById(id);
    }

    @Override
    public G update(Long id, U updateModel) {
      return repos.update(id, updateModel);
    }

    @Override
    public void delete(Long id) {
      repos.delete(id);
    }

    @Override
    public BasePageble<G> getAll(F filterModel) {
      return repos.getAll(filterModel);
    }
  }
