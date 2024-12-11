package codingblackfemales.gettingstarted;

import codingblackfemales.action.Action;
import codingblackfemales.action.NoAction;
import codingblackfemales.algo.AlgoLogic;
import codingblackfemales.sotw.SimpleAlgoState;
import codingblackfemales.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAlgoLogic implements AlgoLogic {

    private static final Logger logger = LoggerFactory.getLogger(MyAlgoLogic.class);
 private static final int MAX_CHILD_ORDERS = 5;
    @Override
    public Action evaluate(SimpleAlgoState state) {

        var orderBookAsString = Util.orderBookToString(state);

        logger.info("[MYALGO] The state of the order book is:\n" + orderBookAsString);
        /********
         *
         * Add your logic here....
         *
         */
        // The objective of this challenge is to write a simple trading algo that creates and cancels child orders.

        AskLevel farTouch = state.getAskAt(0);  // Retrieve the best ask price

        int currentChildOrders = state.getChildOrders().size();//current number of child orders

        logger.info("[MYALGO] Current number of child orders: " + currentChildOrders);
           // Checks if the number of current child orders is less than 5 allowed 
            if (currentChildOrders < MAX_CHILD_ORDERS) {
                long quantityToOrder = Math.min(farTouch.quantity, 5);//minimum between the available ask quantity and 5 units

                   

                long priceToOrder = farTouch.price; // price for the new order to be the price of the lowest ask
                logger.info("[MYALGO] Creating a new child order: " + quantityToOrder + " units at price " + priceToOrder);
                    return new CreateChildOrder(Side.BUY, quantityToOrder, priceToOrder); //return a new CreateChildOrder indicating a BUY order with the determined quantity and price
            } else {
                     //Retrieve the oldest child order
                      var oldestChildOrder = state.getChildOrders().get(0);//if enough child orders cancel the oldest order.
                      return new CancelChildOrder(oldestChildOrder);
         }
        return NoAction.NoAction;
    }
}
