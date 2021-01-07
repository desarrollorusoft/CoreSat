package ar.com.cognisys.sat.core.modelo.comun.carrito;

public class Encriptador {
	
	public static String encriptar(String msgStr) {
		
		String keyStr = new PropiedadesCrypt().getClaveEncriptacionXml();
		byte[] msg = byteArray(msgStr);
		byte[] key = byteArray(keyStr);

		if (key.length != 48)
			return null;

		int msgLen = msg.length;
		byte ctrl = 0;
		String msgFinal = "";
		byte part;

		for (int i = 0; i < msgLen; i++) {
			int pos = i % 16;

			if (pos == 0) {
				if (i > 0) {
					part = xor(ctrl, key[32]); // 16 + 17 - 1
					msgFinal = msgFinal + hexaString(part);
				}
				ctrl = 0;
			}
			part = xor(msg[i], key[pos]);
			ctrl = xor(ctrl, part);

			part = xor(part, key[16 + pos]);

			msgFinal = msgFinal + hexaString(part);

		}

		part = xor(ctrl, key[32]); // 16 + 17 - 1
		msgFinal = msgFinal + hexaString(part);

		return msgFinal;
	}

	private static String hexaString(byte a) {
		
		String result = Integer.toHexString(a);
		result = result.length() < 2 ? "0" + result : result.substring(result.length() - 2);
		return result;
	}

	private static byte[] byteArray(String s) {
		try {
			return s.getBytes("ISO-8859-1");
		} catch (Exception ex) {
			return null;
		}
	}

	private static byte xor(byte b1, byte b2) {
		return (byte) (b1 ^ b2);
	}
}