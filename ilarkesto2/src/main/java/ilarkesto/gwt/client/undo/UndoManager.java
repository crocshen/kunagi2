/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.gwt.client.undo;

import static ilarkesto.core.logging.ClientLog.INFO;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 */
public class UndoManager {

	private final int maxOperations = 5;
	private final List<AUndoOperation> operations = new LinkedList<AUndoOperation>();

    /**
     *
     * @param operation
     */
    public void add(AUndoOperation operation) {
		operations.add(0, operation);
		while (operations.size() > maxOperations) {
			operations.remove(operations.size() - 1);
		}
	}

    /**
     *
     * @param operation
     */
    public void undo(AUndoOperation operation) {
		INFO("Undo:", operation);
		operation.undo();
		operations.remove(operation);
	}

    /**
     *
     * @return
     */
    public List<AUndoOperation> getOperations() {
		return operations;
	}

    /**
     *
     */
    public void clear() {
		operations.clear();
	}
}
