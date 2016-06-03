// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.admin;

public class ChangePasswordServiceCall extends generated.client.core.AServiceCall {

    private String newPassword;

    private String oldPassword;

    public  ChangePasswordServiceCall(String newPassword, String oldPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().changePassword(serviceCaller.getConversationNumber(), newPassword, oldPassword, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "ChangePassword";
    }

}

