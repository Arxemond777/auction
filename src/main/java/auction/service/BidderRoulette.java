package auction.service;


import auction.model.BidderImpl;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.signum;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

public class BidderRoulette {
    private final int START_QUANTITY = 0;
    private final List<List<String>> lots;
    private final List<BidderImpl> bidders;

    public BidderRoulette() {
        this(null, -1);
    }

    public BidderRoulette(List<List<String>> lots, int cache) {
        if (isNull(lots))
            this.lots = asList(
                    asList("lot11","lot12"),
                    asList("lot21","lot22")
            );
        else
            this.lots = lots;

        if (signum(cache) == -1)
            cache = 100;

        // INIT bidders
        this.bidders = new ArrayList<>();

        BidderImpl bidder1 = new BidderImpl(bidders);
        bidder1.init(START_QUANTITY, cache);
        this.bidders.add(bidder1);

        BidderImpl bidder2 = new BidderImpl(bidders);
        bidder2.init(START_QUANTITY, cache);
        this.bidders.add(bidder2);
    }

    public List<BidderImpl> detectAuctionWinner() {
        for (int lotIdx = 0; lotIdx < lots.size(); lotIdx++) {
            int theMaxRate = 0;
            List<Integer> bidderWinnerIdx = new ArrayList<>();

            for (int bidderIdx = 0; bidderIdx < bidders.size(); bidderIdx++) {
                final BidderImpl bidder = bidders.get(bidderIdx);

                int rate = bidder.placeBid();
                if (theMaxRate < rate) {  // overwrite rate
                    bidderWinnerIdx.clear();
                    bidderWinnerIdx.add(bidderIdx);
                    theMaxRate = rate;
                } else if (theMaxRate == rate) {
                    bidderWinnerIdx.add(bidderIdx);
                }

                bidder.subtractCash(rate);

            }

            WinnerDetector.detectLocalWinner(bidderWinnerIdx, bidders);

            System.out.println("Winner for a lot " + bidderWinnerIdx);
        }

        System.out.println(bidders);

        return WinnerDetector.detectWinner(bidders);
    }

}
