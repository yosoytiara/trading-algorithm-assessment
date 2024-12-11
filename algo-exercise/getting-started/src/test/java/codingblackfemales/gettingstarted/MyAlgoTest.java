package codingblackfemales.gettingstarted;

import codingblackfemales.algo.AlgoLogic;
import org.junit.Test;


/**
 * This test is designed to check your algo behavior in isolation of the order book.
 *
 * You can tick in market data messages by creating new versions of createTick() (ex. createTick2, createTickMore etc..)
 *
 * You should then add behaviour to your algo to respond to that market data by creating or cancelling child orders.
 *
 * When you are comfortable you algo does what you expect, then you can move on to creating the MyAlgoBackTest.
 *
 */
public class MyAlgoTest extends AbstractAlgoTest {

    @Override
    public AlgoLogic createAlgoLogic() {
        //this adds your algo logic to the container classes
        return new MyAlgoLogic();
    }


    @Test
    public void testDispatchThroughSequencer() throws Exception {

        //create a sample market data tick....
        send(createTick());

        //simple assert to check we had 3 orders created
        //assertEquals(container.getState().getChildOrders().size(), 3);
    }
     @Test
    void testEvaluate_DoesNothing_WhenNoValidAskPrice() {
        // Given: a mock state with no valid ask price
        when(mockState.getChildOrders()).thenReturn(List.of());  // No child orders
        when(mockState.getAskAt(0)).thenReturn(null);  // No ask price

        // When: the algorithm evaluates the state
        Action result = algoLogic.evaluate(mockState);

        // Then: it should take no action
        assertEquals(NoAction.NoAction, result);
    }
    @Test
     void testEvaluate_CancelsOldestOrder_WhenTooManyChildOrders() {
        // Given: A mock state with 4 child orders and the max allowed child orders is 3
        when(mockState.getChildOrders()).thenReturn(List.of(
            new ChildOrder(100, 5),  // 5 units at price 100
            new ChildOrder(101, 10), // 10 units at price 101
            new ChildOrder(102, 8),  // 8 units at price 102
            new ChildOrder(103, 7)   // 7 units at price 103
        ));
        when(mockState.getAskAt(0)).thenReturn(new AskLevel(104, 12));  // New ask price

        // When: The algorithm evaluates the state
        Action result = algoLogic.evaluate(mockState);

        // Then: It should cancel the oldest order (first one at price 100)
        assertTrue(result instanceof CancelChildOrder);
        CancelChildOrder cancelOrder = (CancelChildOrder) result;
        assertEquals(cancelOrder.getOrder(), mockState.getChildOrders().get(0));  // It should cancel the first order
    }
}
