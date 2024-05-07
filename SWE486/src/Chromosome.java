import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
public class Chromosome {
    private Region[] regions;
    private String[] availableColors;

    public Chromosome(Region[] regions, String[] colors) {
        this.regions = regions;
        this.availableColors = colors;
        assignRandomColors();
    }

    private void assignRandomColors() {
        Random random = new Random();
        for (Region region : regions) {
            region.MyColor = availableColors[random.nextInt(availableColors.length)];
        }
    }

    public int calculateFitness() {
        int fitness = 0;
        for (Region region : regions) {
            fitness += region.checkConflicts();
        }
        return fitness;
    }

    public Chromosome mutate() {
        Random random = new Random();
        regions[random.nextInt(regions.length)].MyColor = availableColors[random.nextInt(availableColors.length)];
        return this;
    }

    public static Chromosome crossOver(Chromosome p1, Chromosome p2, int point) {
        Region[] newRegions = new Region[p1.regions.length];
        System.arraycopy(p1.regions, 0, newRegions, 0, point);
        System.arraycopy(p2.regions, point, newRegions, point, p2.regions.length - point);
        return new Chromosome(newRegions, p1.availableColors);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Region region : regions) {
            result.append(region.MyColor).append(" ");
        }
        return result.toString().trim();
    }
}
