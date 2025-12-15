package ru.kifor.kek.base.repository;

import org.jooq.Record;
import org.jooq.impl.TableImpl;
import ru.kifor.kek.base.model.*;

import java.util.List;

public interface BaseRepository<
    C extends BaseCreateModel,
    G extends BaseModel,
    U extends BaseUpdateModel,
    F extends BaseFilterModel
    > {

  public G create(C createModel);
  public G getById(Long id);
  public G update(Long id, U updateModel);

  public void delete(Long id);
  public BasePageble<G> getAll(F filterModel);


}
