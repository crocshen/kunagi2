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
package ilarkesto.form;

import ilarkesto.base.MissingDependencyException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 *
 * @param <T>
 */
public final class RadioSelectFormField<T> extends AFormField {

	private List<T> options;
	private T value;
	private boolean required = true;
	private boolean vertical;

    /**
     *
     * @param name
     */
    public RadioSelectFormField(String name) {
		super(name);
	}

    /**
     *
     * @param options
     * @return
     */
    public RadioSelectFormField<T> setOptions(Collection<T> options) {
		this.options = new ArrayList<>(options);
		return this;
	}

    /**
     *
     * @param value
     * @return
     */
    public RadioSelectFormField<T> setValue(T value) {
		this.value = value;
		return this;
	}

    /**
     *
     * @param vertical
     * @return
     */
    public RadioSelectFormField<T> setVertical(boolean vertical) {
		this.vertical = vertical;
		return this;
	}

    /**
     *
     * @return
     */
    public boolean isVertical() {
		return vertical;
	}

    /**
     *
     * @return
     */
    public List<T> getOptions() {
		if (options == null) {
                        throw new MissingDependencyException("options");
                }
		return options;
	}

    /**
     *
     * @return
     */
    public T getValue() {
		return value;
	}

    /**
     *
     * @return
     */
    @Override
	public String getValueAsString() {
		return value == null ? null : value.toString();
	}

    /**
     *
     * @param data
     * @param uploadedFiles
     */
    @Override
	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		String indexAsString = data.get(getName());
		if (indexAsString == null) {
			value = null;
		} else {
			value = options.get(parseInt(indexAsString));
		}
	}

    /**
     *
     * @throws ValidationException
     */
    @Override
	public void validate() throws ValidationException {
		if (value == null && required) {
                        throw new ValidationException("Eingabe erforderlich");
                }
	}

}
