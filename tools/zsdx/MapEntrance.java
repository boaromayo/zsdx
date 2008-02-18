package zsdx;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

/**
 * Represents an entry point in a map.
 */
public class MapEntrance extends InteractiveEntity implements ImageObserver {
    
    /**
     * Icons of an entrance for each direction. 
     */
    private static ImageIcon[] icons;

    // load the icons
    static {
	String path = "zsdx/images/";

	icons = new ImageIcon[4];
	for (int i = 0; i < 4; i++) {
	    icons[i] = new ImageIcon(path + "map_entrance_" + i + ".png");
	}
    }
    
    /**
     * Common constructor to create a new or an existing map entrance
     * @param map the map
     * @param x x coordinate of the entrance (can be zero to set it later)
     * @param y y coordinate of the entrance (can be zero to set it later)
     * @param computeDefaultName true if this is a new entrance
     */
    private MapEntrance(Map map, int x, int y, boolean computeDefaultName) {
	super(map, LAYER_LOW, x, y, 16, 16, computeDefaultName);

	setHotSpot(8, 16);
	setDirection(1);
    }

    /**
     * Creates a new map entrance at the specified location.
     * @param map the map
     * @param x x coordinate of the entrance
     * @param y y coordinate of the entrance
     */
    public MapEntrance(Map map, int x, int y) {
	this(map, x, y, true);
    }

    /**
     * Creates an existing map entrance from a string.
     * @param tokenizer the string tokenizer, which has already parsed the common part of the string
     * (i.e. the layer, the coordinates, the direction and the type of entity have already been handled)
     * @param map the map
     * @throws ZSDXException if there is a syntax error in the string
     */
    public MapEntrance(StringTokenizer tokenizer, Map map) throws ZSDXException {
	this(map, 0, 0, false);
    }

    /**
     * Returns an integer identifying the kind of entity.
     * @return MapEntity.ENTITY_ENTRANCE
     */
    public int getType() {
	return ENTITY_ENTRANCE;
    }

    /**
     * Draws the entrance on the map editor.
     * @param g graphic context
     * @param zoom zoom of the image (for example, 1: unchanged, 2: zoom of 200%)
     * @param showTransparency true to make transparent pixels,
     * false to replace them by a background color
     */
    public void paint(Graphics g, double zoom, boolean showTransparency) {
	
	int scale = (int) zoom;

	if (scale != 2) {
	    throw new UnsupportedOperationException("Zoom mode not yet supported: " + zoom);
	}
	
	int dx1 = positionInMap.x * scale;
	int dy1 = positionInMap.y * scale;

	int dx2 = dx1 + positionInMap.width * scale;
	int dy2 = dy1 + positionInMap.height * scale;

	if (showTransparency) {
	g.drawImage(icons[getDirection()].getImage(), dx1, dy1, dx2, dy2, 0, 0, 32, 32, this);
	}
	else {
		g.drawImage(icons[getDirection()].getImage(), dx1, dy1, dx2, dy2, 0, 0, 32, 32, bgColor, this);		
	}
    }

    /**
     * This function is called when some requested information about the image comes.
     * @return true
     */
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
	return true;
    }

    /**
     * Returns the number of possible directions of the entity.
     * @return 4
     */
    public int getNbDirections() {
	return 4;
    }
    
    /**
     * Returns whether the entity has an identifier.
     * @return true
     */
    public boolean hasName() {
	return true;
    }
}
