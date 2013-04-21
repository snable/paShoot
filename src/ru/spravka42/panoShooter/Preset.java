/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.spravka42.panoShooter;

import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class Preset {
	
	public ArrayList<Shoot> shoots;
    
    public Preset(ArrayList<Shoot> shoots) {
        this.shoots = shoots;
    }
    
    public int getShootsCount() {
    	return shoots.size();
    }
    
}
