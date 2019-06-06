package auction.service;

import auction.BidderImpl;
import auction.exception.BusinessException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static auction.service.WinnerDetector.detectLocalWinner;
import static auction.service.WinnerDetector.detectWinner;
import static java.util.stream.Collectors.toList;

public class WinnerDetectorTest {

    @Test
    public void detectLocalDefault() {
        final List<BidderImpl> bidders = IntStream.range(0, 2)
                .boxed()
                .map(v -> {
                    final BidderImpl bidder = new BidderImpl(null);
                    bidder.init(0, 1);

                    return bidder;
                })
                .collect(toList());

        List<Integer> bidderWinnerIdx = new ArrayList<>();
        bidderWinnerIdx.add(1);
        bidderWinnerIdx.add(0);

        detectLocalWinner(bidderWinnerIdx, bidders);
    }

    @Test(expected = BusinessException.class)
    public void detectLocalWinnerIncorrectIdx() {
        final List<BidderImpl> bidders = IntStream.range(0, 2)
                .boxed()
                .map(v -> {
                    final BidderImpl bidder = new BidderImpl(null);
                    bidder.init(0, 1);

                    return bidder;
                })
                .collect(toList());

        List<Integer> bidderWinnerIdx = new ArrayList<>();
        bidderWinnerIdx.add(1);
        bidderWinnerIdx.add(2);

        detectLocalWinner(bidderWinnerIdx, bidders);
    }

    @Test(expected = BusinessException.class)
    public void detectLocalWinnerIncorrectInit() {
        final List<BidderImpl> bidders = IntStream.range(0, 2)
                .boxed()
                .map(v -> {
                    final BidderImpl bidder = new BidderImpl(null);
                    bidder.init(0, 1);

                    return bidder;
                })
                .collect(toList());

        List<Integer> bidderWinnerIdx = new ArrayList<>();
        bidderWinnerIdx.add(2);
        bidderWinnerIdx.set(0, null);

        detectLocalWinner(bidderWinnerIdx, bidders);
    }

    @Test
    public void winnerDetectorDefault() {
        final List<BidderImpl> bidders = IntStream.range(0, 2)
                .boxed()
                .map(v -> {
                    final BidderImpl bidder = new BidderImpl(null);
                    bidder.init(0, 1);

                    return bidder;
                })
                .collect(toList());

        detectWinner(bidders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void winnerDetectorIncorrectSize() {
        final List<BidderImpl> bidders = IntStream.range(0, 3)
                .boxed()
                .map(v -> {
                    final BidderImpl bidder = new BidderImpl(null);
                    bidder.init(0, 1);

                    return bidder;
                })
                .collect(toList());

        detectWinner(bidders);
    }

    @Test(expected = BusinessException.class)
    public void winnerDetectorUninitBindder() {
        final List<BidderImpl> bidders = IntStream.range(0, 2)
                .boxed()
                .map(v -> new BidderImpl(null))
                .collect(toList());

        detectWinner(bidders);
    }
}
