import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Graph {
    private final List<Artiste> artistes = new ArrayList<>();
    private final Map<String, Integer> nomVersIndex = new HashMap<>();
    private final Map<Integer, Integer> idVersIndex = new HashMap<>();
    private final List<List<Mention>> listeAdjacence = new ArrayList<>();

    public Graph(String fichierArtistes, String fichierMentions) {
        try {
            Files.lines(Paths.get(fichierArtistes))
                    .filter(ligne -> !ligne.trim().isEmpty())
                    .forEach(ligne -> {
                        String[] parties = ligne.split(",", 3);
                        if (parties.length == 3) {
                            try {
                                int id = Integer.parseInt(parties[0]);
                                String nom = parties[1];
                                String categorie = parties[2];

                                int index = artistes.size();
                                artistes.add(new Artiste(id, nom, categorie));
                                nomVersIndex.put(nom, index);
                                idVersIndex.put(id, index);
                                listeAdjacence.add(new LinkedList<>());  // Utilisation de LinkedList au lieu de ArrayList
                            } catch (NumberFormatException e) {
                                System.err.println("Format invalide artists.txt: " + ligne);
                            }
                        }
                    });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try (Scanner scanner = new Scanner(new File(fichierMentions))) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine().trim();
                if (!ligne.isEmpty()) {
                    String[] parties = ligne.split(",", 3);
                    if (parties.length == 3) {
                        try {
                            int idSource = Integer.parseInt(parties[0]);
                            int idDestination = Integer.parseInt(parties[1]);
                            int poids = Integer.parseInt(parties[2]);

                            Integer indexSource = idVersIndex.get(idSource);
                            Integer indexDestination = idVersIndex.get(idDestination);

                            if (indexSource != null && indexDestination != null) {
                                listeAdjacence.get(indexSource)
                                        .add(new Mention(indexDestination, poids));
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Format invalide mentions.txt: " + ligne);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void trouverCheminLePlusCourt(String artiste1, String artiste2) {

    }

    public void trouverCheminMaxMentions(String artiste1, String artiste2) {
        Integer depart = nomVersIndex.get(artiste1);
        Integer arrivee = nomVersIndex.get(artiste2);

        double[] distances = new double[artistes.size()];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[depart] = 0;

        int[] avant = new int[artistes.size()];
        Arrays.fill(avant, -1);

        PriorityQueue<double[]> file = new PriorityQueue<>(Comparator.comparingDouble(e -> e[1]));
        file.add(new double[]{depart, 0});

        while (!file.isEmpty()) {
            double[] element = file.poll();
            int actuel = (int) element[0];
            if (actuel == arrivee) break;
            if (element[1] > distances[actuel]) continue;

            for (Mention m : listeAdjacence.get(actuel)) {
                double distanceCalculee = distances[actuel] + 1.0 / m.getPoids();
                if (distanceCalculee < distances[m.getDestination()]) {
                    distances[m.getDestination()] = distanceCalculee;
                    avant[m.getDestination()] = actuel;
                    file.add(new double[]{m.getDestination(), distanceCalculee});
                }
            }
        }

        if (distances[arrivee] == Double.POSITIVE_INFINITY) {
            System.out.println("Chemin inexistant");
            return;
        }

        List<Integer> parcours = new ArrayList<>();
        for (int etape = arrivee; etape != -1; etape = avant[etape]) parcours.add(etape);
        Collections.reverse(parcours);

        System.out.println("Longueur du chemin : " + (parcours.size() - 1) +
                "\nCoût total : " + distances[arrivee] +
                "\nChemin :");
        parcours.forEach(indice -> System.out.println(artistes.get(indice).getNom() + " (" + artistes.get(indice).getType() + ")"));
    }
}