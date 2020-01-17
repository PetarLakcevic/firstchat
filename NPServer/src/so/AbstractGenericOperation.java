/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import storage.IDatabaseBroker;
import storage.database.DatabaseBroker;
import storage.database.connection.DatabaseConnection;

/**
 *
 * @author student1
 */
public abstract class AbstractGenericOperation {

    protected IDatabaseBroker databaseBroker;

    public AbstractGenericOperation() {
        databaseBroker = new DatabaseBroker();
    }

    public final void templateExecute(Object entity) throws Exception {
        try {
            validate(entity);
            try {
                startTransaction();
                execute(entity);
                commitTransaction();
            } catch (Exception ex) {
                rollbackTransaction();
                throw ex;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    protected abstract void validate(Object entity) throws Exception;

    private void startTransaction() throws Exception {
        DatabaseConnection.getInstance().startTransaction();
    }

    protected abstract void execute(Object entity) throws Exception;

    private void commitTransaction() throws Exception {
        DatabaseConnection.getInstance().commitTransaction();
    }

    private void rollbackTransaction() throws Exception {
        DatabaseConnection.getInstance().rollbackTransaction();
    }

}
