/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author sasav
 */
public interface Dao {

    void delete(Object object);

    <T> List<T> getAll(Class<T> clazz);

    Object getById(String clazzString, final long id);

    Object getById(String clazzString, final String id);

    List getByQuery(final String queryS, final Object[] values);

    List getByQuery(final String queryS);
    
    void savaOrUpdateAll(Collection coll);

    void saveOrUpdate(Object object);
    
}
