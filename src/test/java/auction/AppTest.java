package auction;

import auction.exception.BusinessException;
import auction.model.BidderImpl;
import auction.service.BidderRoulette;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class AppTest {
    @Test
    public void testDefault() {
        final List<BidderImpl> bidders = new BidderRoulette().detectAuctionWinner();

        if (bidders.size() == 2) {
            assertEquals(1, bidders.get(0).getQuantity());
            assertEquals(1, bidders.get(1).getQuantity());
        } else if (bidders.size() == 1) {
            final int quantity = bidders.get(0).getQuantity();
            assertTrue(quantity >= 1 && quantity <= 4);
        } else {
            throw new BusinessException("Insufficient bidders size");
        }
    }

    @Test
    public void testMoreLots() {
        final List<List<String>> lots = asList(
                asList("lot11", "lot12"),
                asList("lot11", "lot12"),
                asList("lot11", "lot12"),
                asList("lot11", "lot12"),
                asList("lot11", "lot12"),
                asList("lot21", "lot22")
        );

        final List<BidderImpl> bidders = new BidderRoulette(lots, 100).detectAuctionWinner();

        if (bidders.size() == 2) {
            assertEquals(lots.size(), bidders.get(0).getQuantity());
            assertEquals(lots.size(), bidders.get(1).getQuantity());
        } else if (bidders.size() == 1) {
            final int quantity = bidders.get(0).getQuantity();
            assertTrue(quantity >= 6 && quantity <= 12);
        } else {
            throw new BusinessException("Insufficient bidders size");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntOverflow() {
        final List<List<String>> lots = asList(
                asList("lot11", "lot12"),
                asList("lot11", "lot12"),
                asList("lot21", "lot22")
        );

        new BidderRoulette(lots, Integer.MAX_VALUE).detectAuctionWinner();
    }
}
