
package dvdrental;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author susie
 */
@Named(value = "filmController")
@SessionScoped
public class FilmController implements Serializable {

    int startId;
    
    // this field will map to the table in the index.xhtml
    DataModel filmTitles;
    
    // this is our class that uses Hibernate to query the database
    FilmHelper helper;
    
    // these fields are needed for the previous and next functionality
    private int recordCount;
    private int pageSize = 10;
    
    // this field is needed to get the movie selected in the index.xhtml
    private Film selected;
    
    // these fields map to components in the browse.xhtml
    String language;
    String actors;
    String category;
    
    // this field is needed to get the id of the movie selected
    // it will be mapped to a hidden component in the email.xhtml
    private int selectedId;
    
    // these fields map to components in the email.xhtml
    String emailAddress;
    String emailResponse;
    
    /**
     * Creates a new instance of FilmController
     */
    public FilmController() {
        helper = new FilmHelper();
        startId = 0;
        recordCount = helper.getNumberFilms();
    }

    public DataModel getFilmTitles() {
        if (filmTitles == null){
            filmTitles = new ListDataModel(helper.getFilmTitles(startId));
        }
        return filmTitles;
    }

    public void setFilmTitles(DataModel filmTitles) {
        this.filmTitles = filmTitles;
    }
    
    // this method sets our field that maps to the table in the index.xhtml to null 
    // if this field is null when the page reloads, then we'll retrieve more films
    // it then initializes recordCount to the number of total films in the film table
    private void recreateModel(){
        filmTitles = null;
        recordCount = helper.getNumberFilms();
    }
    
    // this method increments our startId field, calls recreateModel, and
    // returns index
    // returning this String will force the page to reload
    public String next(){
        startId = startId + (pageSize + 1);
        recreateModel();
        return "index";
    }
    
    // this method increments our startId field, calls recreateModel, and
    // returns index
    // returning this String will force the page to reload
    public String previous(){
        startId = startId - pageSize;
        recreateModel();
        return "index";
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    // this method will return true if it makes sense to
    // display the Next hyperlink
    public boolean isHasNextPage(){
        if (startId + pageSize < recordCount){
            return true;
        }
        return false;
    }
    
    // this method will return true if it makes sense to
    // display the Pervious hyperlink
    public boolean isHasPreviousPage(){
        if (startId - pageSize > 0){
            return true;
        }
        return false;
    }

    public String getLanguage() {
        // get the language id of the selected movie
        int langID = selected.getLanguageByLanguageId().getLanguageId().intValue();
        // call helper method that gets the language based on language id
        language = helper.getLangByID(langID);
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getActors() {
        // call helper method that returns all of the actors associated with
        // selected film
        List actors = helper.getActorsByID(selected.getFilmId());
        // create a String that contains the names of all of the actors
        StringBuilder cast = new StringBuilder();
        for (int i = 0; i < actors.size(); i++){
            Actor actor = (Actor) actors.get(i);
            cast.append(actor.getFirstName());
            cast.append(" ");
            cast.append(actor.getLastName());
            cast.append("  ");
        }
        return cast.toString();
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getCategory() {
        // call helper method that returns the category of the selected film
        Category category = helper.getCategoryByID(selected.getFilmId());
        return category.getName();
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Film getSelected() {
        if (selected == null){
            selected = new Film();
        }
        return selected;
    }

    public void setSelected(Film selected) {
        this.selected = selected;
    }
    
    // this method will get called when the View hyperlink gets selected
    public String prepareView(){
        // get all of the data associated with the selected movie
        selected = (Film) getFilmTitles().getRowData();
        // return the name of the page that will load when the hyperlink
        // is selected
        return "browse";
    }
    
    // this method will get called when the View All List hyperlink get selected
    // on the browse.xhtml
    public String prepareList(){
        // get the list of movies again
        recreateModel();
        // return the name of the page that will load when the hyperlink
        // is selected
        return "index";
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailResponse() {
        if (emailAddress != null){
            int result = 0;
            
            Film film = helper.getFilmID(selectedId);
            
            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<table width=\"100%\" height=\"100%\" style=");
            emailBody.append("\"background-color: transparent; border-spacing: 0; border-collapse: collapse; border-top: 1px solid #ddd\"");
            emailBody.append("><tbody>");
            emailBody.append("<tr><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");      
            emailBody.append(">Title</td><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");
            emailBody.append(">");
            emailBody.append(film.getTitle());
            emailBody.append("</td></tr>");
            emailBody.append("<tr><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");      
            emailBody.append(">Description</td><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");
            emailBody.append(">");
            emailBody.append(film.getDescription());
            emailBody.append("</td></tr>");
            emailBody.append("<tr><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");      
            emailBody.append(">Film Length</td><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");
            emailBody.append(">");
            emailBody.append(film.getLength());
            emailBody.append("</td></tr>");
            emailBody.append("<tr><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");      
            emailBody.append(">Release Year</td><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");
            emailBody.append(">");
            emailBody.append(film.getReleaseYear());
            emailBody.append("</td></tr>");
            emailBody.append("<tr><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");      
            emailBody.append(">Rating</td><td style=");
            emailBody.append("\"padding: .5em 1em; vertical-align: top; text-align: left; border-bottom: 1px solid #ddd\"");
            emailBody.append(">");
            emailBody.append(film.getRating());
            emailBody.append("</td></tr>");
            emailBody.append("</tbody></table>");
            
            String subject = film.getTitle() + " Details";
            
            result = HTMLEmailHelper.send(emailAddress, subject, emailBody.toString());
            
            if (result == 1){
                emailAddress = null;
                emailResponse = "Email Sent.";
                return emailResponse;
            } else {
                emailAddress = null;
                emailResponse = "Email Not Sent.";
                return emailResponse;
            }
        } else {
            emailResponse = " ";
            return emailResponse;
        }
    }

    public void setEmailResponse(String emailResponse) {
        this.emailResponse = emailResponse;
    }
    
    // this method will get called when the Email hyperlink on the index.xhtml
    // gets clicked
    public String prepareEmail(){
        // get all of the data associated with the selected movie
        selected = (Film) getFilmTitles().getRowData();
        // get the id of the selected movie
        selectedId = selected.getFilmId().intValue();
        // return the name of the page that will load when the hyperlink
        // is selected
        return "email";
    }
}
