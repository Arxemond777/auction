package auction.enums;

/**
 * The following are states reflected by this enum : a draw or a win
 */
public enum WinOrDraw {
    WIN(2), DRAW(1);

    private final int quantity;

    WinOrDraw(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
