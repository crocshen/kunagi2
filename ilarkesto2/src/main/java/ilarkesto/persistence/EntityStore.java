/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.persistence;

import ilarkesto.fp.Predicate;
import ilarkesto.id.IdentifiableResolver;
import java.util.Collection;
import java.util.Set;

/**
 *
 *
 */
public interface EntityStore extends IdentifiableResolver<AEntity> {

    /**
     *
     * @param version
     */
    void setVersion(long version);

    /**
     *
     * @param alias
     * @param cls
     */
    void setAlias(String alias, Class cls);

    /**
     *
     * @param cls
     * @param alias
     */
    void load(Class<? extends AEntity> cls, String alias);

    /**
     *
     * @param typeFilter
     * @param entityFilter
     * @return
     */
    AEntity getEntity(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter);

    /**
     *
     * @param typeFilter
     * @param entityFilter
     * @return
     */
    int getEntitiesCount(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter);

    /**
     *
     * @param typeFilter
     * @param entityFilter
     * @return
     */
    Set<AEntity> getEntities(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter);

    /**
     *
     * @param entitiesToSave
     * @param entitiesToDelete
     */
    void persist(Collection<AEntity> entitiesToSave, Collection<AEntity> entitiesToDelete);

    /**
     *
     */
    void lock();

    /**
     *
     */
    void deleteOldBackups();

}
