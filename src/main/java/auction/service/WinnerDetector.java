package auction.service;

import auction.model.BidderImpl;
import auction.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static auction.enums.WinOrDraw.WIN;
import static auction.enums.WinOrDraw.DRAW;
import static java.util.Objects.isNull;

class WinnerDetector {

    /**
     * Validator for WinnerDetector
     */
    private static class WinnerDetectorValidator {
        private static void biddersValidator(List<Integer> bidderWinnerIdx, List<BidderImpl> bidders) {
            biddersValidator(bidders);

            if (bidderWinnerIdx.size() == 1) {
                final Integer first = bidderWinnerIdx.get(0);
                if (isNull(first) || !(first == 0 || first == 1)) throw new BusinessException();
            } else if (bidderWinnerIdx.size() == 2) {
                final Integer first = bidderWinnerIdx.get(0);
                final Integer second = bidderWinnerIdx.get(1);
                if (
                        isNull(first) || !(first == 0 || first == 1)
                                || isNull(second) || !(second == 0 || second == 1)
                ) throw new BusinessException("Incorrect bidderWinnerIdx list");
            }
        }

        private static void biddersValidator(List<BidderImpl> bidders) {
            if (isNull(bidders) || bidders.size() != 2)
                throw new IllegalArgumentException("Size of list bidders must be equal 2");
            bidders.forEach(bidder -> {
                if (bidder.getQuantity() < 0 || bidder.getCash() < 0)
                    throw new BusinessException("A bidder hasnt`t been initializing");
            });
        }
    }

    static List<BidderImpl> detectWinner(List<BidderImpl> bidders) {
        WinnerDetectorValidator.biddersValidator(bidders);

        final List<BidderImpl> winnerByCriteriaQuantity = findWinnerByCriteria(BidderImpl::getQuantity, bidders);

        if (winnerByCriteriaQuantity.size() == 1) // if different quantity
            return winnerByCriteriaQuantity;

        return findWinnerByCriteria(BidderImpl::getCash, bidders);
    }

    /**
     * Find a winner by a criteria
     *
     * @param f       - a function with a required criteria
     * @param bidders
     * @return - list with a winner/winners
     */
    private static List<BidderImpl> findWinnerByCriteria(Function<BidderImpl, Integer> f, List<BidderImpl> bidders) {
        List<BidderImpl> winner = new ArrayList<>();

        for (BidderImpl bidder : bidders) {
            if (winner.isEmpty())
                winner.add(bidder);
            else if (f.apply(winner.get(0)) < f.apply(bidder))
                winner.set(0, bidder);
            else if (f.apply(winner.get(0)).equals(f.apply(bidder)))
                winner.add(bidder);
        }

        return winner;
    }

    /**
     * Datec winner in a concrete round
     * @param bidderWinnerIdx - idx list with a winner or winners idx
     * @param bidders
     */
    static void detectLocalWinner(List<Integer> bidderWinnerIdx, List<BidderImpl> bidders) {
        WinnerDetectorValidator.biddersValidator(bidderWinnerIdx, bidders);

        if (bidderWinnerIdx.size() == 1) { // detect a winner for a lot
            final BidderImpl bidder = bidders.get(bidderWinnerIdx.get(0));
            bidder.addQuantity(WIN);
        } else if (bidderWinnerIdx.size() == 2) {
            bidders.forEach(bidder -> bidder.addQuantity(DRAW));
        }
    }
}
