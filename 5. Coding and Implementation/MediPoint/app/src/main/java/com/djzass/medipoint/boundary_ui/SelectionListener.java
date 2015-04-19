package com.djzass.medipoint.boundary_ui;

/**
 * Created by Deka on 8/4/2015.
 *
 * Interface for selecting an item from a fragment
 *
 * @author Deka
 * @since 2015
 * @version 1.0
 */
public interface SelectionListener {
    /**
     * Abstract method that handles the selection of the item.
     *
     * @param position of the item being selected in a fragment
     */
    public void selectItem ( int position );
}
