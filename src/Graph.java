import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Graph {

    private Map<Integer, Artiste> artisteMap;
    private Map<Integer, List<Mention>> mentionMap;

    public Graph(Path artistFilePath, Path mentionFilePath) throws IOException {
        artisteMap = new HashMap<>();
        mentionMap = new HashMap<>();

        // Traitement afin de charger les artistes dans le Graph
        try (BufferedReader readerA = Files.newBufferedReader(artistFilePath, Charset.defaultCharset())) {
            String ligneArtiste;
            while ((ligneArtiste = readerA.readLine()) != null) {
                ligneArtiste = ligneArtiste.trim();
                if (!ligneArtiste.isEmpty()) {
                    String[] ligneSplitArtiste = ligneArtiste.split(",");
                    if (ligneSplitArtiste.length == 3) {
                        int id = Integer.parseInt(ligneSplitArtiste[0]);
                        String nom = ligneSplitArtiste[1].trim();
                        String type = ligneSplitArtiste[2].trim();
                        artisteMap.put(id, new Artiste(id, nom, type));
                    }
                }
            }
        }

        // Traitement afin de charger les mentions dans le Graph
        try (BufferedReader readerM = Files.newBufferedReader(mentionFilePath, Charset.defaultCharset())) {
            String ligneMention;
            while ((ligneMention = readerM.readLine()) != null) {
                ligneMention = ligneMention.trim();
                if (!ligneMention.isEmpty()) {
                    String[] ligneSplitMention = ligneMention.split(",");
                    if (ligneSplitMention.length == 3) {
                        int id1 = Integer.parseInt(ligneSplitMention[0]);
                        int id2 = Integer.parseInt(ligneSplitMention[1]);
                        int mention = Integer.parseInt(ligneSplitMention[2]);

                        double poids = 1.0 / mention;

                        mentionMap.computeIfAbsent(id1, k -> new ArrayList<>()).add(new Mention(id2, poids));
                        mentionMap.computeIfAbsent(id2, k -> new ArrayList<>()).add(new Mention(id1, poids));
                    }
                }
            }
        }
    }

    public void trouverCheminLePlusCourt(String origine, String arrive) {
    }

    public void trouverCheminMaxMentions(String theBeatles, String kendjiGirac) {
    }


    public void afficherContenu() {
        System.out.println("=== Artistes chargés ===");
        for (Map.Entry<Integer, Artiste> entry : artisteMap.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " | Nom: " + entry.getValue().getNom() + " | Type: " + entry.getValue().getType());
        }

        System.out.println("\n=== Mentions chargées ===");
        for (Map.Entry<Integer, List<Mention>> entry : mentionMap.entrySet()) {
            System.out.print("ID :" + entry.getKey() + " mentions : ");
            System.out.println("\n");
            for (Mention mention : entry.getValue()) {
                System.out.print("[vers ID " + mention.getArtisteId() + " poids=" + mention.getPoids() + "] ");
            }
            System.out.println();
        }
    }

}