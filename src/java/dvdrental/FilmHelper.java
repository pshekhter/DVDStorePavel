
package dvdrental;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author susie
 */
public class FilmHelper {
    
    Session session = null;
    
    // When using hibernate to execute queries against a database, the
    // queries need to be executed in transactions.  In order to
    // begin a transaction, you must first have a session.
    public FilmHelper() {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List getFilmTitles(int startID){
        
        List<Film> filmList = null;
        
        // create the query, but as a String
        // :start and :end, are placeholders for actual values
        // passed in as parameters and hard-coded
        String sql = "select * from film order by title limit :start, :end";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(Film.class);
            // bind values to the query placeholders
            q.setParameter("start", startID);
            q.setParameter("end", 10);
            
            // execute the query and cast the returned List
            // as a List of Films
            filmList = (List<Film>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
                
        return filmList;
    }
    
    public int getNumberFilms(){
        
        List<Film> filmList = null;
        
        // create the query, but as a String
        String sql = "select * from film";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(Film.class);
            
            // execute the query and cast the returned List
            // as a List of Films
            filmList = (List<Film>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return filmList.size();
    }
    
    public List getActorsByID (int filmId){
        
        List<Actor> actorList = null;
        
        // create the query, but as a String
        // :id is a placeholder for actual value
        // passed in as parameter 
        String sql = "select * from actor, film_actor, film "
                + "where actor.actor_id = film_actor.actor_id "
                + "and film_actor.film_id = film.film_id "
                + "and film.film_id = :id";
        
        try {
            
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Actor POJO and table with the query 
            q.addEntity(Actor.class);
            
            // bind value to the query placeholder
            q.setParameter("id", filmId);
            
            // execute the query and cast the returned List
            // as a List of Actors
            actorList = (List<Actor>) q.list();
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return actorList;
    }
    
    public Category getCategoryByID (int filmId){
        
        List<Category> categoryList = null;
        
        // create the query, but as a String
        // :id is a placeholder for actual value
        // passed in as parameter 
        String sql = "select * from category, film_category, film "
                + "where category.category_id = film_category.category_id "
                + "and film_category.film_id = film.film_id "
                + "and film.film_id = :id";
        
        try {
            
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(Category.class);
            
            // bind value to the query placeholder
            q.setParameter("id", filmId);
            
            // execute the query and cast the returned List
            // as a List of Categories
            categoryList = (List<Category>) q.list();
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return categoryList.get(0);
    }
    
    public Film getFilmID (int filmId) {
        
        Film film = null;
        
        // create the query, but as a String
        // :id is a placeholder for actual value
        // passed in as parameter
        String sql = "select * from film where film_id = :id";
        
        try {
            
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Film POJO and table with the query 
            q.addEntity(Film.class);
            
            // bind value to the query placeholder
            q.setParameter("id", filmId);
            
            // execute the query and cast the return value
            // to a Film
            film = (Film) q.uniqueResult();
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return film;
    }
    
    public String getLangByID (int langId) {
        
        Language language = null;
        
        // create the query, but as a String
        // :id is a placeholder for actual value
        // passed in as parameter
        String sql = "select * from language where language_id = :id";
        
        try {
            
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Language POJO and table with the query 
            q.addEntity(Language.class);
            
            // bind value to the query placeholder
            q.setParameter("id", langId);
            
            // execute the query and cast the return value
            // to a Film
            language = (Language) q.uniqueResult();
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return language.getName();
    }
}
