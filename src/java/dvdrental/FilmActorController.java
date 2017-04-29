package dvdrental;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author susie
 */
@Named(value = "filmActorController")
@SessionScoped
public class FilmActorController implements Serializable {

    // These fields map to the options in the select components in the film.xhtml
    DataModel actorValues;
    DataModel categoryValues;
    DataModel languageValues;

    // this is our class that uses Hibernate to query the database
    FilmActorHelper helper;

    // these fields map to the components in the film.xhtml
    // they represent the actual values input and selected by the user
    String title;
    String description;
    int actor;
    String rating;
    int category;
    int language;
    String response;

    /**
     * Creates a new instance of FilmActorController
     */
    public FilmActorController() {
        helper = new FilmActorHelper();
    }

    public DataModel getActorValues() {
        if (actorValues == null) {
            // call helper method that gets the List of Actors
            // and pass it to the ListDataModel constructor
            actorValues = new ListDataModel(helper.getActors());
        }
        return actorValues;
    }

    public void setActorValues(DataModel actorValues) {
        this.actorValues = actorValues;
    }

    public DataModel getCategoryValues() {
        if (categoryValues == null) {
            // call helper method that gets the List of Categories
            // and pass it to the ListDataModel constructor
            categoryValues = new ListDataModel(helper.getCategories());
        }
        return categoryValues;
    }

    public void setCategoryValues(DataModel categoryValues) {
        this.categoryValues = categoryValues;
    }

    public DataModel getLanguageValues() {
        if (languageValues == null) {
            // call helper method that gets the List of Languages
            // and pass it to the ListDataModel constructor
            languageValues = new ListDataModel(helper.getLanguages());
        }
        return languageValues;
    }

    public void setLanguageValues(DataModel languageValues) {
        this.languageValues = languageValues;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActor() {
        return actor;
    }

    public void setActor(int actor) {
        this.actor = actor;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getResponse() {
        // if the title and description components in the film.xhtml
        // have data in them, then insert film into the database
        if (title != null && description != null) {

            // get the current timestamp in SQL format
            Date date = new Date();
            Timestamp timeStamp = new Timestamp(date.getTime());

            // call the helper method that inserts the data into
            // the database
            if (helper.insert(title, description, actor, category, language, rating, timeStamp) == 1) {
                title = null;
                description = null;
                response = "Film Added.";
                return response;
            } else {
                title = null;
                description = null;
                response = "Film Not Added.";
                return response;
            }
        } else {
            // if nothing was input into the form
            response = " ";
            return response;
        }
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
