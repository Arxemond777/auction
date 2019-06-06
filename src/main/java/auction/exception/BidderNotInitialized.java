package auction.exception;

public class BidderNotInitialized extends RuntimeException {
    public BidderNotInitialized() {
        super();
    }

    public BidderNotInitialized(String message) {
        super(message);
    }
}
