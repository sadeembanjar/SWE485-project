
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        //take inputs from the user
        System.out.println("Welcome to Search algorithm with Incremental Formulation for map coloring!");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of regions: ");
        int NumRegions = Integer.parseInt(scanner.next());
        String strRegion = "";
        Region[] Regions = new Region[NumRegions];

        System.out.println("Enter the number of colors: ");
        int Numcolors = Integer.parseInt(scanner.next());
        String[] colors = new String[Numcolors];

        for (int i = 0; i < Numcolors; i++) {
            System.out.println("Enter the color " + (i + 1) + ": ");
            colors[i] = scanner.next();
        }
        for (int i = 0; i < NumRegions; i++) {
            Regions[i] = new Region(colors, "R" + i);
            strRegion += "R" + i + ",";
        }
        for (int i = 0; i < NumRegions; i++) {
            System.out.println("Enter the adjacent Regions (just numbers 1,2,3...) for R" + (i) + " from " + strRegion + " or -1 for none ");
            String o = scanner.next();
            if (!o.equals("-1")) {
                String[] ads = o.split(",");
                Region[] adRegions = new Region[ads.length];

                int x = 0;
                for (String ad : ads) {
                    adRegions[x] = Regions[Integer.parseInt(ad)];
                    x++;
                }
                Regions[i].setAdjacents(adRegions);
            }
        }

        scanner.close();

        //timer
        long startTime = System.nanoTime();

        boolean allSet = true;
        // the start of the algorithm (assigning colors)
        for (Region r : Regions) {
            r.setMyColor();
            if (!r.isColorSet)
                allSet = false;
        }
        if (allSet) {
            System.err.println("The Solution:");
            for (Region r : Regions) {
                System.err.println(r.toString());
            }
        } else {
            System.err.println("There is no solution");
        }
    
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  
        System.out.println("Computational time in milliseconds: " + duration / 1_000_000);
    }
}
