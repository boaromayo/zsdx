/*
 * Copyright (C) 2009 Christopho, Zelda Solarus - http://www.zelda-solarus.com
 * 
 * Zelda: Mystery of Solarus DX is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Zelda: Mystery of Solarus DX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package zsdx.gui.edit_entities;

import java.awt.event.*;

import zsdx.*;
import zsdx.entities.*;
import zsdx.entities.Enemy.*;
import zsdx.gui.*;
import zsdx.map_editor_actions.*;

/**
 * A component to edit an enemy.
 */
public class EditEnemyComponent extends EditEntityComponent {

    // specific fields
    private EnumerationChooser<Enemy.Rank> rankField;
    private NumberChooser savegameVariableField;
    private PickableItemSubtypeChooser pickableItemSubtypeField;
    private NumberChooser pickableItemSavegameVariableField;

    /**
     * Constructor.
     * @param map the map
     * @param entity the entity to edit
     */
    public EditEnemyComponent(Map map, MapEntity entity) {
	super(map, entity);
    }

    /**
     * Creates the specific fields for this kind of entity.
     */
    protected void createSpecificFields() {

	// rank
	rankField = new EnumerationChooser<Rank>(Rank.class);
	addField("Rank", rankField);

	// savegame variable
	savegameVariableField = new NumberChooser(0, 0, 32767);
	addField("Enemy savegame variable", savegameVariableField);

	// pickable item type
	pickableItemSubtypeField = new PickableItemSubtypeChooser(true);
	addField("Pickable item", pickableItemSubtypeField);

	// pickable item savegame variable
	pickableItemSavegameVariableField = new NumberChooser(0, 0, 32767);
	addField("Pickable item savegame variable", pickableItemSavegameVariableField);

	// enable or disable the 'savegame variable' field depending on the pickable item type
	pickableItemSubtypeField.addActionListener(new ActionListenerEnableSavegameVariable());
    }

    /**
     * Updates the information displayed in the fields.
     */
    public void update() {
	super.update(); // update the common fields

	Enemy enemy = (Enemy) entity;

	rankField.setValue(Rank.get(enemy.getIntegerProperty("rank")));
	savegameVariableField.setNumber(enemy.getIntegerProperty("savegameVariable"));
	pickableItemSubtypeField.setValue(PickableItem.Subtype.get(enemy.getIntegerProperty("pickableItemSubtype")));
	pickableItemSavegameVariableField.setNumber(enemy.getIntegerProperty("pickableItemSavegameVariable"));
	new ActionListenerEnableSavegameVariable().actionPerformed(null);
    }

    /**
     * Returns the specific part of the action made on the entity.
     * @return the specific part of the action made on the entity
     */
    protected ActionEditEntitySpecific getSpecificAction() {

	int pickableItemSavegameVariable = pickableItemSavegameVariableField.isEnabled() ? 
		pickableItemSavegameVariableField.getNumber() : -1;

	return new ActionEditEntitySpecific(entity,
		rankField.getValue().getId(),
		savegameVariableField.getNumber(),
		pickableItemSubtypeField.getValue().getId(),
		pickableItemSavegameVariable);
    }

    /**
     * A listener associated to the 'pickable item type' field,
     * to enable or disable the 'savegame variable' field depending on the type.
     */
    private class ActionListenerEnableSavegameVariable implements ActionListener {

	public void actionPerformed(ActionEvent ev) {
	    PickableItem.Subtype pickableItemSubtype = pickableItemSubtypeField.getValue();
	    pickableItemSavegameVariableField.setEnabled(pickableItemSubtype.isSaved());
	}
    }
}
