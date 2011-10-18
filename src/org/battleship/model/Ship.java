/**
 * 
 */
package org.battleship.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songoku
 *
 */
public class Ship {
	public int size;
	public boolean isAlive;
	public List<Section> usingSections = new ArrayList<Section>();
	public List<Section> damageSections = new ArrayList<Section>();
	
	public void addSector( Section sector ){
		this.usingSections.add( sector );
	}
}
