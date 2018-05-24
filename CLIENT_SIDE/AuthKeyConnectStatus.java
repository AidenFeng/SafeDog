

public class AuthKeyConnectStatus {
	private static AuthKeyConnectStatus authKeyConnectStatus;
	private boolean status;
	static{
		authKeyConnectStatus = new AuthKeyConnectStatus();
	}

	public static AuthKeyConnectStatus getInstance(){
		return authKeyConnectStatus;
	}

	public synchronized void setStatus(boolean status){
		this.status = status;
	}

	public synchronized boolean getStatus(){
		return status;
	}
}
