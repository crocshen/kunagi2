// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.collaboration;

import ilarkesto.core.base.KunagiProperties;
import java.util.*;
import static ilarkesto.core.base.Utl.equalObjects;
import static ilarkesto.core.logging.ClientLog.*;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GSubject
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GSubject() {
    }

    public GSubject(KunagiProperties data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "subject";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- project ---

    private String projectId;

    public final scrum.client.project.Project getProject() {
        if (projectId == null) return null;
        return getDao().getProject(this.projectId);
    }

    public final boolean isProjectSet() {
        return projectId != null;
    }

    public final Subject setProject(scrum.client.project.Project project) {
        String id = project == null ? null : project.getId();
        if (equalObjects(this.projectId, id)) return (Subject) this;
        this.projectId = id;
        propertyChanged("projectId", this.projectId);
        return (Subject)this;
    }

    public final boolean isProject(scrum.client.project.Project project) {
        return equalObjects(this.projectId, project);
    }

    // --- label ---

    private java.lang.String label ;

    public final java.lang.String getLabel() {
        return this.label ;
    }

    public final Subject setLabel(java.lang.String label) {
        if (isLabel(label)) return (Subject)this;
        if (ilarkesto.core.base.Str.isBlank(label)) throw new RuntimeException("Field is mandatory.");
        this.label = label ;
        propertyChanged("label", this.label);
        return (Subject)this;
    }

    public final boolean isLabel(java.lang.String label) {
        return equalObjects(this.label, label);
    }

    private transient LabelModel labelModel;

    public LabelModel getLabelModel() {
        if (labelModel == null) labelModel = createLabelModel();
        return labelModel;
    }

    protected LabelModel createLabelModel() { return new LabelModel(); }

    protected class LabelModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Subject_label";
        }

        @Override
        public java.lang.String getValue() {
            return getLabel();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLabel(value);
        }

        @Override
        public boolean isMandatory() { return true; }
        @Override
        public String getTooltip() { return "The subject this discussion will be listed under in the project's forum."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- text ---

    private java.lang.String text ;

    public final java.lang.String getText() {
        return this.text ;
    }

    public final Subject setText(java.lang.String text) {
        if (isText(text)) return (Subject)this;
        this.text = text ;
        propertyChanged("text", this.text);
        return (Subject)this;
    }

    public final boolean isText(java.lang.String text) {
        return equalObjects(this.text, text);
    }

    private transient TextModel textModel;

    public TextModel getTextModel() {
        if (textModel == null) textModel = createTextModel();
        return textModel;
    }

    protected TextModel createTextModel() { return new TextModel(); }

    protected class TextModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Subject_text";
        }

        @Override
        public java.lang.String getValue() {
            return getText();
        }

        @Override
        public void setValue(java.lang.String value) {
            setText(value);
        }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "Notes that give background information and summarize discussion results."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- number ---

    private int number ;

    public final int getNumber() {
        return this.number ;
    }

    public final Subject setNumber(int number) {
        if (isNumber(number)) return (Subject)this;
        this.number = number ;
        propertyChanged("number", this.number);
        return (Subject)this;
    }

    public final boolean isNumber(int number) {
        return equalObjects(this.number, number);
    }

    private transient NumberModel numberModel;

    public NumberModel getNumberModel() {
        if (numberModel == null) numberModel = createNumberModel();
        return numberModel;
    }

    protected NumberModel createNumberModel() { return new NumberModel(); }

    protected class NumberModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "Subject_number";
        }

        @Override
        public java.lang.Integer getValue() {
            return getNumber();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setNumber(value);
        }

            @Override
            public void increment() {
                setNumber(getNumber() + 1);
            }

            @Override
            public void decrement() {
                setNumber(getNumber() - 1);
            }

        @Override
        public boolean isMandatory() { return true; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- update properties by map ---

    public void updateProperties(KunagiProperties props) {
        projectId = (String) props.getValue("projectId");
        label  = (java.lang.String) props.getValue("label");
        text  = (java.lang.String) props.getValue("text");
        number  = (Integer) props.getValue("number");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(KunagiProperties properties) {
        super.storeProperties(properties);
        properties.putValue("projectId", this.projectId);
        properties.putValue("label", this.label);
        properties.putValue("text", this.text);
        properties.putValue("number", this.number);
    }

    @Override
    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getText(), key)) return true;
        return false;
    }

}