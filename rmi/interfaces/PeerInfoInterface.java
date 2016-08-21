package rmi.interfaces;

import java.io.Serializable;

public interface PeerInfoInterface extends Serializable {
    public String getUUID();
    public String getIp();
    public String getUserName();
}
