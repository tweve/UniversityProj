
public class MobilityBindingTableEntry {
	
	private String MN;
	private String HoA;
	private String CoA;
	private int lifeTime;
	
	public MobilityBindingTableEntry(String mn,String noA, String coA, int lifeTime) {
		MN = mn;
		HoA = noA;
		CoA = coA;
		this.lifeTime = lifeTime;
	}

	public String getMN() {
		return MN;
	}

	public void setMN(String mN) {
		MN = mN;
	}
	public String getHoA() {
		return HoA;
	}
	public void setNoA(String noA) {
		HoA = noA;
	}
	public String getCoA() {
		return CoA;
	}
	public void setCoA(String coA) {
		CoA = coA;
	}
	public int getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}
	
	
}
