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
            AskLevel farTouch = state.getAskAt(0);
        int currentChildOrders = state.getChildOrders().size();
 logger.info("[MYALGO] Current number of child orders: " + currentChildOrders);
        if (currentChildOrders < MAX_CHILD_ORDERS) {
                long quantityToOrder = Math.min(farTouch.quantity, 5);
                long priceToOrder = farTouch.price;
                logger.info("[MYALGO] Creating a new child order: " + quantityToOrder + " units at price " + priceToOrder);
        return new CreateChildOrder(Side.BUY, quantityToOrder, priceToOrder);
                } else {
                      var oldestChildOrder = state.getChildOrders().get(0);
                      return new CancelChildOrder(oldestChildOrder);
                }
        return NoAction.NoAction;
    }
}
