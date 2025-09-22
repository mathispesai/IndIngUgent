package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.Coordinate;
import be.ugent.devops.services.logic.api.Location;


public class BaseProbability {
    private static final int BASE = 100000; // Maximum probability value
    private static final int CLEARED = -100000;   // Minimum probability value
    private final int factionId;
    private final int[][] probabilities; // 2D array for probabilities
    private final int height, width;

    private static final String RESET = "\u001B[0m";   // Resets the console color

    private static final String GREY = "\u001B[37m";   // Grey (light grey)

    public BaseProbability(int width, int height, int factionId) {
        this.height = height;
        this.width = width;
        this.probabilities = new int[this.height][this.width];
        this.factionId = factionId;
        initializeProbabilities();
    }

    private void initializeProbabilities() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                probabilities[i][j] = 1;
            }
        }
    }

    // Update probabilities based on a single location
    public void updateProbabilities(Location loc, Boolean minimal) {
        int x = loc.getX();
        int y = loc.getY();

        if (!isInBounds(x, y)) {
            return; // Ignore updates for out-of-bounds locations
        }

        if (loc.getOccupyingUnit().isPresent() && loc.getOccupyingUnit().get().owner() != factionId) {
            // If there's an occupying unit, increase probability
            adjustNeighbors(x - 1, x + 1, y - 1, y + 1, 1, 20, 1);
        }

        if(!minimal) {
            if (loc.isBase() ) {//If location is a base set prob to max (or min in case of our base) and decrease neighboring tiles
                if(loc.getOwner().isPresent() && loc.getOwner().get() == factionId){
                    probabilities[y][x] = CLEARED;
                    adjustNeighbors(x-1, x+1, y-1, y+1, -256, 0, 16);
//                adjustNeighbors(x-1, x+1, y-1, y+1, -100, 0, 2);
                }else{
                    probabilities[y][x] = BASE;
                    adjustNeighbors(x-1, x+1, y-1, y+1, -256, 0, 16);
//                adjustNeighbors(x-1, x+1, y-1, y+1, -100, 0, 2);
                }
            }else{//If it's not a base set prob to min
                probabilities[y][x] = CLEARED;
            }

            if (loc.getOwner().isEmpty()) {
                adjustNeighbors(x - 1, x + 1, y - 1, y + 1, -5, -1, 1);
            }
            if (loc.getOwner().isPresent() && loc.getOwner().get() == factionId) {
                // If the owner is the same faction, decrease probability
                adjustNeighbors(x - 1, x + 1, y - 1, y + 1, -10, -1, 1);
            } else if (loc.getOwner().isPresent() && loc.getOwner().get() != factionId) {
                // If the owner is not the same faction, increase probability
                adjustNeighbors(x - 1, x + 1, y - 1, y + 1, 1, 10, 1);
            }

            if (loc.isRigged() && loc.getOwner().isPresent() && loc.getOwner().get() != factionId) {
                // If the tile is rigged, increase probability
                adjustNeighbors(x - 1, x + 1, y - 1, y + 1, 0, 20, +5);
            }

            if (loc.isFortified() && loc.getOwner().isPresent() && loc.getOwner().get() != factionId) {
                adjustNeighbors(x - 1, x + 1, y - 1, y + 1, 20, 0, -2);
            }
        }
    }

    // Adjust probabilities for neighbors
    private void adjustNeighbors(int min_x, int max_x, int min_y, int max_y, int val, int end, int delta) {
        min_x = (min_x + width) % width;
        max_x = (max_x + width) % width;
        min_y = (min_y + height) % height;
        max_y = (max_y + height) % height;

        for (int i = min_y; i <= max_y; i++) {
            for (int j = min_x; j <= max_x; j++) {
                probabilities[i][j] += val;
            }
        }
        int new_val = val + delta;
        if(new_val != end){
            adjustNeighbors(min_x - 1, max_x + 1, min_y - 1, max_y + 1, new_val, end, delta);
        }
    }

    // Check if a coordinate is within grid bounds
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }


    public Coordinate getMaxProbabilityLocation() {
        int maxProbability = Integer.MIN_VALUE;
        int maxX = -1;
        int maxY = -1;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (probabilities[i][j] > maxProbability) {
                    maxProbability = probabilities[i][j];
                    maxX = j;
                    maxY = i;
                }
            }
        }
        return maxX != -1 ? new Coordinate(maxX, maxY) : null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Coordinate max = getMaxProbabilityLocation();
        System.out.println(max.toString());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(i == max.getY() && j == max.getX()){
                    sb.append(rgbColor(0, 0, 0))
                            .append("O")
                            .append(RESET);
                }else{
                    sb.append(getColorForProbability(probabilities[i][j]))
                            .append("O")
                            .append(RESET);
                }

            }
            sb.append("\n"); // New line at the end of each row
        }

        return sb.toString();
    }


    private String getColorForProbability(int probability) {
        if (probability > 10000) {
            return rgbColor(0, 255, 0); // Bright Green for very high probabilities
        } else if (probability > 256) {
            return rgbColor(255, 255, 0); // Bright Yellow for overflow above 256
        } else if (probability > 1) {
            // Map to Red-to-Yellow Gradient
            int red = 255;
            int green = Math.min(255, (probability * 255) / 256); // Scale green to probability
            int blue = 0;
            return rgbColor(red, green, blue);
        } else if (probability == 1) {
            return GREY;
        } else if (probability > -256) {
            // Map to Cyan-to-Blue Gradient
            int green = Math.max(0, 255 + (probability * 255) / 256); // Scale green inversely
            int blue = Math.min(255, 255 - (probability * 255) / 256); // Scale blue to probability
            int red = 0;
            return rgbColor(red, green, blue);
        } else {
            return rgbColor(0, 0, 255); // Blue for very low negatives
        }
    }

    private String rgbColor(int r, int g, int b) {
        return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
    }

}
