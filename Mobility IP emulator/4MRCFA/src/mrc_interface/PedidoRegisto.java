package mrc_interface;

import java.io.Serializable;

public class PedidoRegisto implements Serializable {

		/**
	 * 
	 */
	private static final long serialVersionUID = -5695730512628046857L;
		String NodeName;
		String MAC;
		String HoA;
		String CoA;
		String password;

		public String getNodeName() {
			return NodeName;
		}

		public void setNodeName(String nodeName) {
			NodeName = nodeName;
		}

		public String getMAC() {
			return MAC;
		}

		public void setMAC(String mAC) {
			MAC = mAC;
		}

		public String getHoA() {
			return HoA;
		}

		public void setHoA(String hoA) {
			HoA = hoA;
		}

		public String getCoA() {
			return CoA;
		}

		public void setCoA(String coA) {
			CoA = coA;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		
		
}
