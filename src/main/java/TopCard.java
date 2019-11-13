public class TopCard {

    private Card card;
    private Colors declaredColor;

    public TopCard (){}

    public TopCard(Card card, Colors declaredColor){
        this.card=card;
        this.declaredColor=declaredColor;
    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Colors getDeclaredColor() {
        return declaredColor;
    }

    public void setDeclaredColor(Colors declaredColor) {
        this.declaredColor = declaredColor;
    }
}
