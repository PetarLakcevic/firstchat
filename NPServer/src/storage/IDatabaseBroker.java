/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import domain.GeneralEntity;
import java.util.List;

/**
 *
 * @author student1
 */
public interface IDatabaseBroker {

    List<GeneralEntity> getAll(GeneralEntity entity) throws Exception;

    List<GeneralEntity> getAll(GeneralEntity entity, String keyword) throws Exception;

    GeneralEntity getOne(GeneralEntity entity) throws Exception;

    public long save(List<GeneralEntity> entities) throws Exception;

    public boolean remove(GeneralEntity entity) throws Exception;
}
