package Repo;

import com.example.project.Model.CartItem;
import com.example.project.Repo.CartItemConnector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * CartItemConnector Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 8, 2021</pre>
 */
public class CartItemConnectorTest {

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
        Assert.assertNotNull(CartItemConnector.getInstance());

    }

    /**
     * Method: insertCartItem(CartItem cartItem)
     */
    @Test
    public void testInsertCartItem() throws Exception {
        CartItem cartItem = new CartItem(99, "Some Movie", 100);
        cartItem.setOid(99);
        Assert.assertTrue(CartItemConnector.getInstance().insertCartItem(cartItem));
    }

    /**
     * Method: getCartItemsByOid(Integer oid)
     */
    @Test
    public void testGetCartItemsByOid() throws Exception {
        Assert.assertNotNull(CartItemConnector.getInstance().getCartItemsByOid(1));
    }


} 
