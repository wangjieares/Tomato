// MessageAidlInterface.aidl
package www.atomato.com;
import www.atomato.com.MessageManager;
// Declare any non-default types here with import statements

interface MessageAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    MessageManager getDemand();
    void setDemandIn(in MessageManager msg);//客户端->服务端
    void setDemandOut(in MessageManager msg);//服务端->客户端
    void setDemandInOut(in MessageManager msg);//服务端<->客户端
}
