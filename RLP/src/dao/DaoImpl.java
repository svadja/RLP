/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sasav
 */
@Repository
public class DaoImpl implements Dao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void saveOrUpdate(Object object) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(object);
        session.flush();
        session.close();
    }

    
    @Override
    public void savaOrUpdateAll(Collection coll) {
        Session session = sessionFactory.openSession();
        for(Iterator it = coll.iterator(); it.hasNext();){        
        session.saveOrUpdate(it.next());
        }
        session.flush();
        session.close();
    }

    @Override
    public Object getById(String clazzString, final long id) {
        Session session = sessionFactory.openSession();
        Object result = session.get(clazzString, id);
        session.close();
        return result;
    }

    @Override
    public Object getById(String clazzString, final String id) {
        Session session = sessionFactory.openSession();
        Object result = session.get(clazzString, id);
        session.close();
        return result;
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        Session session = sessionFactory.openSession();
        List result = session.createQuery("from " + clazz.getName()).list();
        session.close();
        return result;
    }

    @Override
    public void delete(Object object) {
        Session session = sessionFactory.openSession();
        session.delete(object);
        session.flush();
        session.close();
    }

    //execute
    @Override
    public List getByQuery(final String queryS, final Object[] values) throws DataAccessException {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(queryS);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        List result = (List) query.list();
       
        session.close();
        return result;
    }

    @Override
    public List getByQuery(String queryS) {
        Session session = sessionFactory.openSession();
        List result = session.createSQLQuery(queryS).list();
        session.close();
        return result;
    }
}
