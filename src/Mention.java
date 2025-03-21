public class Mention {

    private int artisteId;
    private double poids;

    public Mention(int artiste, double cout) {
        this.artisteId = artiste;
        this.poids = cout;
    }

    public int getArtisteId() {
        return artisteId;
    }

    public double getPoids() {
        return poids;
    }

    public void setArtisteId(int artisteId) {
        this.artisteId = artisteId;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }
}
