// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.files;

import static ilarkesto.core.logging.ClientLog.DEBUG;

public class FileUploadedEvent extends ilarkesto.core.event.AEvent {

    private scrum.client.files.File file;

    public  FileUploadedEvent(scrum.client.files.File file) {
        this.file = file;
    }

    public scrum.client.files.File getFile() {
        return file;
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof FileUploadedHandler) {
            DEBUG("    " + handler.getClass().getName() + ".onFileUploaded(event)");
            ((FileUploadedHandler)handler).onFileUploaded(this);
        }
    }

}

