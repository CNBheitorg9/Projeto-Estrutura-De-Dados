import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StressTest {

    public static void main(String[] args) {
        int totalEntries = 1000000;
        System.out.println("[*] Gerando seed com " + totalEntries + " regras ordenadas...");
        
        List<PacketRule> seedData = generateSeedData(totalEntries);

        AVL_Router_Tree avlTree = new AVL_Router_Tree();
        RedBlack_Router_Tree rbtTree = new RedBlack_Router_Tree();

        System.out.println("\n=================================");
        System.out.println("   TESTE 1: ÁRVORE AVL");
        System.out.println("=================================");
        runStressTestAVL(avlTree, seedData);

        System.out.println("\n=================================");
        System.out.println("   TESTE 2: ÁRVORE RED-BLACK");
        System.out.println("=================================");
        runStressTestRBT(rbtTree, seedData);
    }

    public static List<PacketRule> generateSeedData(int totalEntries) {
        List<PacketRule> rules = new ArrayList<>();
        Random rand = new Random(42); 
        for (int i = 1; i <= totalEntries; i++) {
            String ipSrc = "192.168.1." + (i % 254);
            String ipDst = "10.0.0." + (i % 254);
            int priority = rand.nextInt(5) + 1;
            rules.add(new PacketRule(i, ipSrc, ipDst, priority));
        }
        return rules;
    }

    public static void runStressTestAVL(AVL_Router_Tree tree, List<PacketRule> seedData) {
        int totalEntries = seedData.size();

        long startTime = System.nanoTime();
        for (PacketRule rule : seedData) {
            tree.insert(rule);
        }
        long endTime = System.nanoTime();
        System.out.printf("Tempo de Inserção: %,d ns%n", (endTime - startTime));

        int searchCount = (int) (totalEntries * 0.10);
        List<PacketRule> searchSamples = new ArrayList<>(seedData);
        Collections.shuffle(searchSamples, new Random(42));
        searchSamples = searchSamples.subList(0, searchCount);

        startTime = System.nanoTime();
        for (PacketRule rule : searchSamples) {
            tree.search(rule.getId()); 
        }
        endTime = System.nanoTime();
        System.out.printf("Tempo de Busca:    %,d ns%n", (endTime - startTime));

        int deleteCount = (int) (totalEntries * 0.20);
        List<PacketRule> deleteSamples = new ArrayList<>(seedData);
        Collections.shuffle(deleteSamples, new Random(42));
        deleteSamples = deleteSamples.subList(0, deleteCount);

        try {
            startTime = System.nanoTime();
            for (PacketRule rule : deleteSamples) {
                tree.delete(rule.getId()); 
            }
            endTime = System.nanoTime();
            System.out.printf("Tempo de Deleção:  %,d ns%n", (endTime - startTime));
        } catch (Exception e) {
            System.out.println("[ALERTA SRE] A árvore quebrou na deleção: " + e.getMessage());
        }
    }

    public static void runStressTestRBT(RedBlack_Router_Tree tree, List<PacketRule> seedData) {
        int totalEntries = seedData.size();

        long startTime = System.nanoTime();
        for (PacketRule rule : seedData) {
            tree.insert(rule);
        }
        long endTime = System.nanoTime();
        System.out.printf("Tempo de Inserção: %,d ns%n", (endTime - startTime));

        int searchCount = (int) (totalEntries * 0.10);
        List<PacketRule> searchSamples = new ArrayList<>(seedData);
        Collections.shuffle(searchSamples, new Random(42));
        searchSamples = searchSamples.subList(0, searchCount);

        startTime = System.nanoTime();
        for (PacketRule rule : searchSamples) {
            tree.search(rule.getId());
        }
        endTime = System.nanoTime();
        System.out.printf("Tempo de Busca:    %,d ns%n", (endTime - startTime));

        int deleteCount = (int) (totalEntries * 0.20);
        List<PacketRule> deleteSamples = new ArrayList<>(seedData);
        Collections.shuffle(deleteSamples, new Random(42));
        deleteSamples = deleteSamples.subList(0, deleteCount);

        try {
            startTime = System.nanoTime();
            for (PacketRule rule : deleteSamples) {
                tree.delete(rule.getId());
            }
            endTime = System.nanoTime();
            System.out.printf("Tempo de Deleção:  %,d ns%n", (endTime - startTime));
        } catch (Exception e) {
            System.out.println("[ALERTA SRE] A árvore quebrou na deleção: " + e.getMessage());
        }
    }
}