import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph("artists.txt", "mentions.txt");

        graph.trouverCheminLePlusCourt("The Beatles", "Kendji Girac");
        System.out.println("--------------------------");

        graph.trouverCheminMaxMentions("The Beatles", "Kendji Girac");
    }
}