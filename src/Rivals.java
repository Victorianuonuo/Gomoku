import java.net.InetAddress;

public class Rivals {
	private InetAddress address;
    private String name;
    
    public Rivals(String name,InetAddress address) {
		// TODO Auto-generated constructor stub
    	this.name=name;
    	this.address=address;
	}
    
	public InetAddress getAddress() {
		return address;
	}
	public void setAddress(InetAddress address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
	@Override
    public String toString() {
        return name + " at " + address ;
    }

 
	
}
