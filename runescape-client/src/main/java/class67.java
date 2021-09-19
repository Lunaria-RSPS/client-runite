import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

import java.math.BigInteger;

@ObfuscatedName("bm")
public class class67 {
	@ObfuscatedName("n")
	static final BigInteger field867;
	@ObfuscatedName("c")
	static final BigInteger field868;

	static {
		field867 = new BigInteger("10001", 16);
		field868 = new BigInteger("94210824259843347324509385276594109263523823612210415282840685497179394322370180677069205378760490069724955139827325518162089726630921395369270393801925644637806226306156731189625154078707248525519618118185550146216513714101970726787284175941436804270501308516733103597242337227056455402809871503542425244523");
	}

	@ObfuscatedName("jt")
	@ObfuscatedSignature(
		descriptor = "(I)V",
		garbageValue = "1076008723"
	)
	static void method1877() {
		for (InterfaceParent var0 = (InterfaceParent)Client.interfaceParents.first(); var0 != null; var0 = (InterfaceParent)Client.interfaceParents.next()) {
			int var1 = var0.group;
			if (class317.loadInterface(var1)) {
				boolean var2 = true;
				Widget[] var3 = class139.Widget_interfaceComponents[var1];

				int var4;
				for (var4 = 0; var4 < var3.length; ++var4) {
					if (var3[var4] != null) {
						var2 = var3[var4].isIf3;
						break;
					}
				}

				if (!var2) {
					var4 = (int)var0.key;
					Widget var5 = class87.getWidget(var4);
					if (var5 != null) {
						Actor.invalidateWidget(var5);
					}
				}
			}
		}

	}
}
