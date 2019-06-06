package auction.model;

import auction.enums.WinOrDraw;
import auction.exception.BidderNotInitialized;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import static auction.enums.WinOrDraw.DRAW;
import static auction.enums.WinOrDraw.WIN;
import static java.lang.Math.signum;

public class BidderImpl implements Bidder {
    private final int NOT_INIT_VALUE = -1;
    private int
            cash = NOT_INIT_VALUE, // init only once
            quantity = NOT_INIT_VALUE,
            wins, draws, totalBids;

    private final List<BidderImpl> bidders;

    public BidderImpl(List<BidderImpl> bidders) {
        this.bidders = bidders;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getTotalBids() {
        return totalBids;
    }

    /**
     * Add quantity and increased the counter of
     * win/draw depends of get winOrDraw val
     *
     * @param winOrDraw
     */
    public void addQuantity(WinOrDraw winOrDraw) {
        if (WIN == winOrDraw)
            wins++;
        else if (DRAW == winOrDraw)
            draws++;

        this.quantity += winOrDraw.getQuantity();
    }

    public int getQuantity() {
        return quantity;
    }


    public int getCash() {
        return cash;
    }

    /**
     * {@link Bidder#init(int, int)}
     */
    @Override
    public void init(int quantity, int cash) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity a little than 0");
        if (cash < 0) throw new IllegalArgumentException("Cash a little than 0");

        if (this.quantity == NOT_INIT_VALUE)
            this.quantity = quantity;

        if (this.cash == NOT_INIT_VALUE)
            this.cash = cash;
    }

    /**
     * {@link Bidder#placeBid()}
     */
    @Override
    public int placeBid() {
        if (quantity == NOT_INIT_VALUE || cash == NOT_INIT_VALUE)
            throw new BidderNotInitialized("This bidder hasn`t been initialized");

        int rate;
        if (cash <= 0)
            rate = 0;
        else
            rate = new Random().nextInt(cash+1);

        System.out.println("rate > " + rate);
        return rate;
    }

    /**
     * {@link Bidder#bids(int, int)}
     * @param own
     *            the bid of this bidder
     * @param other
     */
    @Override
    public void bids(int own, int other) {
        System.out.println();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++Total stats++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.printf("++ A bidder with id: '%d' have bids '%d', draws '%d, wins '%d' ++\n",
                own, bidders.get(own).getTotalBids(), bidders.get(own).getDraws(), bidders.get(own).getWins());


        System.out.printf("++ A bidder with id: '%d' have bids '%d', draws '%d, wins '%d' ++\n",
                other, bidders.get(other).getTotalBids(), bidders.get(other).getDraws(), bidders.get(other).getWins());

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }

    /**
     * Subtract cash with totalbids increment counter
     * @param cashToSubtract - can be negative or positive, the method detect a required operation automatically
     */
    public void subtractCash(int cashToSubtract) {
        totalBids++;

        int tmp = this.cash;
        synchronized (this) {

            if (signum(cashToSubtract) == NOT_INIT_VALUE)
                tmp += cashToSubtract;
            else if (signum(cashToSubtract) == 1)
                tmp -= cashToSubtract;

            if (signum(tmp) == NOT_INIT_VALUE)
                throw new ConcurrentModificationException("Somebody try to change the same object simultaneously");

            this.cash = tmp;
        }

    }

    @Override
    public String toString() {
        return "BidderImpl{" +
                "cash=" + cash +
                ", quantity=" + quantity +
                '}';
    }
}