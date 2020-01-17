/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author student1
 */
public interface GeneralEntity extends Serializable {

    public String getTableName();

    public String getConstraints(Object keyword);

    public List<GeneralEntity> getList(ResultSet resultSet) throws Exception;

    public GeneralEntity getOne(ResultSet resultSet) throws Exception;

    public String getValues();

}
