package dvdrental;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author susie
 */
public class FilmActorHelper {

    Session session = null;

    // When using hibernate to execute queries against a database, the
    // queries need to be executed in transactions.  In order to
    // begin a transaction, you must first have a session.
    public FilmActorHelper() {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method is going to select all of the actors from the actor table
    public List getActors() {

        List<Actor> actorList = null;

        // create the query, but as a String
        String sql = "select * from actor";

        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Actor POJO and table with the query 
            q.addEntity(Actor.class);
            
            // execute the query and cast the returned List
            // as a List of Actors
            actorList = (List<Actor>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return actorList;
    }
    
    // this method is going to select all of the categories from the
    // category table
    public List getCategories() {
        
        List<Category> categoryList = null;
        
        // create the query, but as a String
        String sql = "select * from category";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(Category.class);
            
            // execute the query and cast the returned List
            // as a List of Categories
            categoryList = (List<Category>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return categoryList;
    }

    // this method is going to select all of the languages from the
    // category table
    public List getLanguages() {
        
        List<Language> languageList = null;
        
        // create the query, but as a String
        String sql = "select * from language";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Language POJO and table with the query 
            q.addEntity(Language.class);
            
            // execute the query and cast the returned List
            // as a List of Languages
            languageList = (List<Language>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return languageList;
    }
    
    // this method is going to insert a film into the film table
    private int insertFilm(String title, String description, int language, 
            String rating, Timestamp timeStamp){
        
        int result = 0;
        
        // create the query, but as a String
        // :title, :description, etc. are placeholders for actual values
        // passed in as parameters
        String sql = "insert into film (title, description, language_id, rental_duration, rental_rate, replacement_cost, rating, last_update) "
                + "values (:title, :description, :languageId, :rentalDuration, :rentalRate, :replacementCost, :rating, :update)";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            // associate the Film POJO and table with the query
            q.addEntity(Film.class);
            // bind values to the query placeholders
            q.setParameter("title", title);
            q.setParameter("description", description);
            q.setParameter("languageId", language);
            q.setParameter("rentalDuration", 3);
            q.setParameter("rentalRate", 4.99);
            q.setParameter("replacementCost", 19.99);
            q.setParameter("rating", rating);
            q.setParameter("update", timeStamp);
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
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    // this method is going to select the film that was just inserted into the
    // film table
    private int getFilmId(){
        
        List<Film> filmList = null;
        
        // create the query, but as a String
        String sql = "select * from film order by last_update desc limit 1";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            // associate the Film POJO and table with the query
            q.addEntity(Film.class);
            // execute the query and cast the returned List
            // as a List of Films
            filmList = (List<Film>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        // return just the id of the first Film in the List of Films
        return filmList.get(0).getFilmId();
    }
    
    // this method is going to insert into the film_actor table
    private int insertFilmActor(int actor, int film, Timestamp timeStamp){
        
        int result = 0;
        
        // create the query, but as a String
        // :actorId, :filmId, etc. are placeholders for actual values
        // passed in as parameters
        String sql = "insert into film_actor values (:actorId, :filmId, :update)";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            // associate the FilmActor POJO and table with the query
            q.addEntity(FilmActor.class);
            // bind values to the query placeholders
            q.setParameter("actorId", actor);
            q.setParameter("filmId", film);
            q.setParameter("update", timeStamp);
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
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    // this method is going to insert into the film_category table
    private int insertFilmCategory(int film, int category, Timestamp timeStamp){
        
        int result = 0;
        
        // create the query, but as a String
        // :categoryId, :filmId, etc. are placeholders for actual values
        // passed in as parameters
        String sql = "insert into film_category values (:filmId, :categoryId, :update)";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            // associate the FilmActor POJO and table with the query
            q.addEntity(FilmCategory.class);
            // bind values to the query placeholders
            q.setParameter("filmId", film);
            q.setParameter("categoryId", category);
            q.setParameter("update", timeStamp);
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
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int insert(String title, String description, int actor, int category, 
            int language, String rating, Timestamp timeStamp){
        
        int result = 0;
        
        // insert into film
        int filmResult = insertFilm(title, description, language, rating, timeStamp);
        // get the id of the inserted film
        int filmId = getFilmId();
        // insert into film_actor
        int actorResult = insertFilmActor(actor, filmId, timeStamp);
        // insert into film_category
        int categoryResult = insertFilmCategory(filmId, category, timeStamp);
        
        // set result equal to 1 if all 3 inserts worked
        if (filmResult == 1 && actorResult == 1 && categoryResult == 1){
            result = 1;
        }
        
        return result;
    }
}
