// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtActionGenerator










package generated.client.project;

import java.util.*;

public abstract class GRejectRequirementAction
            extends scrum.client.common.AScrumAction {

    protected scrum.client.project.Requirement requirement;

    public GRejectRequirementAction(scrum.client.project.Requirement requirement) {
        this.requirement = requirement;
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public String getId() {
        return ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(requirement);
    }

}