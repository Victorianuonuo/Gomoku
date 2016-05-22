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
        return name + " (" + address + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rivals rival = (Rivals) o;

        if (address != null ? !address.equals(rival.address) : rival.address != null) return false;
        return !(name != null ? !name.equals(rival.name) : rival.name != null);

    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
	
}
