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
package ilarkesto.core.scope;

/**
 *
 *
 */
public abstract class ScopeManager {

	private static ScopeManager singletonInstance;

	private Scope rootScope;

    /**
     *
     * @param rootScope
     */
    public ScopeManager(Scope rootScope) {
		assert singletonInstance == null;
		singletonInstance = this;
		this.rootScope = rootScope;
	}

	/**
	 * Gets the active scope.
     * @return 
	 */
	public abstract Scope getScope();

    /**
     *
     * @return
     */
    protected Scope getRootScope() {
		return rootScope;
	}

	/**
	 * Gets the singleton instance of the active ScopeManager.
     * @return 
	 */
	public static ScopeManager getInstance() {
		assert singletonInstance != null;
		return singletonInstance;
	}

	@Override
	public String toString() {
		return getClass().getName() + "(" + getScope() + ")";
	}

}
