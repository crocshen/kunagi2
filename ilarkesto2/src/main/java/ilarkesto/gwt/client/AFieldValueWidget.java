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
package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import static ilarkesto.gwt.client.Gwt.formatHours;

/**
 *
 *
 */
public abstract class AFieldValueWidget extends AWidget {

	private SimplePanel content;
	private HTML viewer;

    /**
     *
     * @return
     */
    @Override
	protected final Widget onInitialization() {
		viewer = new HTML();
		viewer.setStyleName("AFieldValueWidget");
		content = new SimplePanel();
		return content;
	}

    /**
     *
     * @param content
     */
    public void setContent(Widget content) {
		this.content.setWidget(content);
	}

    /**
     *
     * @param text
     */
    public final void setText(Object text) {
		String s = text == null ? null : text.toString();
		viewer.setText(s);
		content.setWidget(viewer);
	}

    /**
     *
     * @param html
     */
    public final void setHtml(String html) {
		viewer.setHTML(html);
		content.setWidget(viewer);
	}

    /**
     *
     * @param hours
     */
    public void setHours(int hours) {
		setText(formatHours(hours));
	}

}
