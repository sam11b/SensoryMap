package hackingheroes.sensorymap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class LocationContent {

    // Array of locations
    public static List<LocationObj> ITEMS = new ArrayList<LocationObj>();

    // Map of locations
    public static Map<String, LocationObj> ITEM_MAP = new HashMap<String, LocationObj>();

    public static void addItem(LocationObj item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    public static void purge() {
        ITEMS = new ArrayList<>();
        ITEM_MAP = new HashMap<>();
    }

    private static String[] makeTags(int position) {
        String tags[] = new String[position];
        for (int i = 0; i < position; i++) {
            tags[i] = "tag";
        }
        return tags;
    }
}
