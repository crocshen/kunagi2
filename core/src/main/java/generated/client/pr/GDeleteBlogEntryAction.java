// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtActionGenerator










package generated.client.pr;

import java.util.*;

public abstract class GDeleteBlogEntryAction
            extends scrum.client.common.AScrumAction {

    protected scrum.client.pr.BlogEntry blogEntry;

    public GDeleteBlogEntryAction(scrum.client.pr.BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public String getId() {
        return ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(blogEntry);
    }

}