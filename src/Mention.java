public class Mention {

    private int destination;
    private double poids;

    public Mention(int artiste, double cout) {
        this.destination = artiste;
        this.poids = cout;
    }

    public int getDestination() {
        return destination;
    }

    public double getPoids() {
        return poids;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }
}
