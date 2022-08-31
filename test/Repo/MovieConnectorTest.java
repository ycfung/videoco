package Repo;

import com.example.project.Model.Movie;
import com.example.project.Repo.MovieConnector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Date;

/**
 * MovieConnector Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 8, 2021</pre>
 */
public class MovieConnectorTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(MovieConnector.getInstance());
    }

    /**
     * Method: addMovie(Movie movie)
     */
    @Test
    public void testAddMovie() throws Exception {
        Movie movie = new Movie("testTitle", "testGenre", "testDirector", "testActors",
                100, new Date().toString(), 9.99);
        Assert.assertNotNull(MovieConnector.getInstance().addMovie(movie));
    }

    /**
     * Method: deleteMovie(Integer mid)
     */
    @Test
    public void testDeleteMovie() throws Exception {
        Assert.assertTrue(MovieConnector.getInstance().deleteMovie(3));
    }


    /**
     * Method: getMovieByID(Integer id)
     */
    @Test
    public void testGetMovieByID() throws Exception {
        Assert.assertNotNull(MovieConnector.getInstance().getMovieByID(1));
    }

    /**
     * Method: getAllMovies()
     */
    @Test
    public void testGetAllMovies() throws Exception {
        Assert.assertNotNull(MovieConnector.getInstance().getAllMovies());
    }

    /**
     * Method: getByCategory(String category)
     */
    @Test
    public void testGetByCategory() throws Exception {
        Assert.assertNotNull(MovieConnector.getInstance().getByCategory("Action"));
    }

    /**
     * Method: getByName(String title)
     */
    @Test
    public void testGetByName() throws Exception {
        Assert.assertNotNull(MovieConnector.getInstance().getByName("Free Guy"));
    }


} 
