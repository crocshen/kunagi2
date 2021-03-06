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
package scrum.client.admin;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.gwt.client.ButtonWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.workspace.PagePanel;

/**
 *
 *
 */
public class UserListWidget extends AScrumWidget {

    /**
     *
     */
    public BlockListWidget<User> list;

    @Override
    protected Widget onInitialization() {
        list = new BlockListWidget<>(UserBlock.FACTORY);
        list.setAutoSorter(User.LAST_LOGIN_COMPARATOR);

        PagePanel page = new PagePanel();
        page.addHeader("Users", new ButtonWidget(new CreateUserAction()));
        page.addSection(list);
        return page;
    }

    @Override
    protected void onUpdate() {
        list.setObjects(getDao().getUsers());
        super.onUpdate();
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean select(User user) {
// Remove error prone test if user exists; the test is not needed        
//        if (!list.contains(user)) {
//            update();
//        }
        update();
        return list.showObject(user);
    }

}
