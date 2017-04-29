package dvdrental;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * This class will use Hibernate to interface with the Actor table.
 *
 * @author susie
 */
public class ActorHelper {

    Session session = null;

    // When using hibernate to execute queries against a database, the
    // queries need to be executed in transactions.  In order to
    // begin a transaction, you must first have a session.
    public ActorHelper() {

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method is going to insert an actor into the actor table
    public int insertActor(Actor a) {

        int result = 0;

        // create the query, but as a String
        // :fName, :lName, and :update are placeholders for actual values
        // in the Actor passed in as a parameter
        String sql = "insert into actor (first_name, last_name, last_update) values (:fName, :lName, :update)";

        try {

            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }

            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            // associate the Actor POJO and table with the query 
            q.addEntity(Actor.class);
            // bind values to the query placeholders
            q.setParameter("fName", a.getFirstName());
            q.setParameter("lName", a.getLastName());
            q.setParameter("update", a.getLastUpdate());
            // execute the query
            // result will be equal to the number of tables
            // that were successfully changed
            result = q.executeUpdate();
            // commit the changes to the database
            // this is what allows the changes to be
            // truely viewed in the database
            // but it also makes the transaction inactive
            // which means it will have to be started again
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
