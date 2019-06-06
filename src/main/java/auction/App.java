package auction;

import auction.service.BidderRoulette;

import java.util.List;

public class App {

    public static void main(String[] args) {

        final List<BidderImpl> bidders = new BidderRoulette().detectAuctionWinner();

        System.out.println("Winners: " + bidders);

        bidders.get(0).bids(0, 1);

    }
}
