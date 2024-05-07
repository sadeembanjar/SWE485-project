import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    int Rnum;
    Region[] Regions;
    List<Region[]> population;
    String[] Colors;

    GeneticAlgorithm(int rnum, String[] col, Region[] regions) {
        this.Rnum = Math.max(rnum, 3);
        this.Regions = regions;
        this.Colors = col;
        population = RandomPopulationGenerator();
        startGeneticAlgorithm();
    }

    public Region[] copyRegionsArray() {
        Region[] newRegions = new Region[Regions.length];
        for (int i = 0; i < Regions.length; i++) {
            newRegions[i] = new Region(Regions[i]); // Use the copy constructor
        }
        return newRegions;
    }

    List<Region[]> RandomPopulationGenerator() {
        List<Region[]> res = new ArrayList<>();
        for (int i = 0; i < Rnum; i++) {
            Region[] copyregions = copyRegionsArray();
            Region[] chrm = new Region[Rnum];
            for (int j = 0; j < Rnum; j++) {
                copyregions[j].setadad(Regions[j], copyregions);
                chrm[j] = copyregions[j];
                chrm[j].MyColor = getRandom(Colors);
            }
            res.add(chrm);
        }
        return res;
    }

    public String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    void startGeneticAlgorithm() {
        boolean done = false;
        while (!done) {
            Collections.sort(population, new Comparator<Region[]>() {
                @Override
                public int compare(Region[] o1, Region[] o2) {
                    return Integer.compare(CalculateCost(o2), CalculateCost(o1));
                }
            });
            Region[] lowestCost = population.get(population.size() - 1);
            if (CalculateCost(lowestCost) == 0) {
                System.err.println("The Solution:");
                for (Region r : lowestCost) {
                    System.err.println(r.toString());
                }
                done = true;
                break;
            }
            population = population.subList(0, 3);
            population.addAll(MakeNewpopulation());
        }
    }

    List<Region[]> MakeNewpopulation() {
        List<Region[]> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Region[] p1 = population.get(i);
            for (int j = i + 1; j < 3; j++) {
                Region[] p2 = population.get(j);
                res.add(NewChild(p1, p2));
            }
        }
        return res;
    }

    private Region[] NewChild(Region[] p1, Region[] p2) {
        Region[] newchild = CrossOver(1, p1, p2);
        newchild = mutate(newchild);
        return newchild;
    }

    int CalculateCost(Region[] chromsome) {
        int fitness = 0;
        for (int i = 0; i < chromsome.length; i++) {
            fitness += chromsome[i].checkConflicts();
        }
        return fitness;
    }

    Region[] mutate(Region[] chromsome) {
        Random random = new Random();
        chromsome[random.nextInt(chromsome.length)].MyColor = getRandom(Colors);
        return chromsome;
    }

    Region[] CrossOver(int point, Region[] p1, Region[] p2) {
        Region[] part1 = Arrays.copyOfRange(p1, 0, point);
        Region[] part2 = Arrays.copyOfRange(p2, point, p2.length);
        Region[] res = new Region[Rnum];
        System.arraycopy(part1, 0, res, 0, part1.length);
        System.arraycopy(part2, 0, res, part1.length, part2.length);
        return res;
    }
 
}
