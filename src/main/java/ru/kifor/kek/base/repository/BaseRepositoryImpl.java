package ru.kifor.kek.base.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kifor.kek.base.model.BaseCreateModel;
import ru.kifor.kek.base.model.BaseFilterModel;
import ru.kifor.kek.base.model.BaseModel;
import ru.kifor.kek.base.model.BaseUpdateModel;
import ru.kifor.kek.utils.NotImplementedException;

import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class BaseRepositoryImpl <
    E extends TableImpl<Record>,
    C extends BaseCreateModel,
    G extends BaseModel,
    U extends BaseUpdateModel,
    F extends BaseFilterModel
    > implements BaseRepository<C,G,U,F>{

  @Autowired
  protected DSLContext dslContext;

  @Override
  public G create(C createModel) {
    throw new NotImplementedException("Method not implemented");
  }

  @Override
  public G getById(Long id) {
    throw new NotImplementedException("Method not implemented");
  }

  @Override
  public G update(Long id, U updateModel) {
    throw new NotImplementedException("Method not implemented");
  }

  @Override
  public void delete(Long id) {
    throw new NotImplementedException("Method not implemented");
  }

  @Override
  public List<G> getAll(F filterModel) {
    throw new NotImplementedException("Method not implemented");
  }
}
