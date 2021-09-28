import net.runelite.mapping.*;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

@ObfuscatedName("bl")
@Implements("ScriptFrame")
public class ScriptFrame {
	@ObfuscatedName("sv")
	@ObfuscatedGetter(
		intValue = 345312141
	)
	static int field447;
	@ObfuscatedName("nr")
	@ObfuscatedGetter(
		intValue = 222177301
	)
	@Export("selectedSpellFlags")
	static int selectedSpellFlags;
	@ObfuscatedName("n")
	@ObfuscatedSignature(
		descriptor = "Lbg;"
	)
	@Export("script")
	Script script;
	@ObfuscatedName("c")
	@ObfuscatedGetter(
		intValue = -1290618837
	)
	@Export("pc")
	int pc;
	@ObfuscatedName("m")
	@Export("intLocals")
	int[] intLocals;
	@ObfuscatedName("k")
	@Export("stringLocals")
	String[] stringLocals;

	ScriptFrame() {
		this.pc = -1;
	}

	@ObfuscatedName("e")
	@ObfuscatedSignature(
		descriptor = "(I)V",
		garbageValue = "-1508115242"
	)
	static void method1085() {
		Login.Login_username = Login.Login_username.trim();
		if (Login.Login_username.length() == 0) {
			PlatformInfo.setLoginResponseString("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
		} else {
			long var1;
			try {
				URL var3 = new URL(WorldMapManager.method3679("services", false) + "m=accountappeal/login.ws");
				URLConnection var4 = var3.openConnection();
				var4.setRequestProperty("connection", "close");
				var4.setDoInput(true);
				var4.setDoOutput(true);
				var4.setConnectTimeout(5000);
				OutputStreamWriter var5 = new OutputStreamWriter(var4.getOutputStream());
				var5.write("data1=req");
				var5.flush();
				InputStream var6 = var4.getInputStream();
				Buffer var7 = new Buffer(new byte[1000]);

				while (true) {
					int var8 = var6.read(var7.array, var7.offset, 1000 - var7.offset);
					if (var8 == -1) {
						var7.offset = 0;
						long var20 = var7.readLong();
						var1 = var20;
						break;
					}

					var7.offset += var8;
					if (var7.offset >= 1000) {
						var1 = 0L;
						break;
					}
				}
			} catch (Exception var28) {
				var1 = 0L;
			}

			byte var0;
			if (var1 == 0L) {
				var0 = 5;
			} else {
				String var29 = Login.Login_username;
				Random var30 = new Random();
				Buffer var24 = new Buffer(128);
				Buffer var9 = new Buffer(128);
				int[] var10 = new int[]{var30.nextInt(), var30.nextInt(), (int)(var1 >> 32), (int)var1};
				var24.writeByte(10);

				int var11;
				for (var11 = 0; var11 < 4; ++var11) {
					var24.writeInt(var30.nextInt());
				}

				var24.writeInt(var10[0]);
				var24.writeInt(var10[1]);
				var24.writeLong(var1);
				var24.writeLong(0L);

				for (var11 = 0; var11 < 4; ++var11) {
					var24.writeInt(var30.nextInt());
				}

				var24.encryptRsa(class65.field849, class65.field853);
				var9.writeByte(10);

				for (var11 = 0; var11 < 3; ++var11) {
					var9.writeInt(var30.nextInt());
				}

				var9.writeLong(var30.nextLong());
				var9.writeLongMedium(var30.nextLong());
				ClientPreferences.method2195(var9);
				var9.writeLong(var30.nextLong());
				var9.encryptRsa(class65.field849, class65.field853);
				var11 = BufferedSink.stringCp1252NullTerminatedByteSize(var29);
				if (var11 % 8 != 0) {
					var11 += 8 - var11 % 8;
				}

				Buffer var12 = new Buffer(var11);
				var12.writeStringCp1252NullTerminated(var29);
				var12.offset = var11;
				var12.xteaEncryptAll(var10);
				Buffer var13 = new Buffer(var12.offset + var24.offset + var9.offset + 5);
				var13.writeByte(2);
				var13.writeByte(var24.offset);
				var13.writeBytes(var24.array, 0, var24.offset);
				var13.writeByte(var9.offset);
				var13.writeBytes(var9.array, 0, var9.offset);
				var13.writeShort(var12.offset);
				var13.writeBytes(var12.array, 0, var12.offset);
				String var14 = Message.method1104(var13.array);

				byte var25;
				try {
					URL var15 = new URL(WorldMapManager.method3679("services", false) + "m=accountappeal/login.ws");
					URLConnection var16 = var15.openConnection();
					var16.setDoInput(true);
					var16.setDoOutput(true);
					var16.setConnectTimeout(5000);
					OutputStreamWriter var17 = new OutputStreamWriter(var16.getOutputStream());
					var17.write("data2=" + VertexNormal.method4478(var14) + "&dest=" + VertexNormal.method4478("passwordchoice.ws"));
					var17.flush();
					InputStream var18 = var16.getInputStream();
					var13 = new Buffer(new byte[1000]);

					while (true) {
						int var19 = var18.read(var13.array, var13.offset, 1000 - var13.offset);
						if (var19 == -1) {
							var17.close();
							var18.close();
							String var26 = new String(var13.array);
							if (var26.startsWith("OFFLINE")) {
								var25 = 4;
							} else if (var26.startsWith("WRONG")) {
								var25 = 7;
							} else if (var26.startsWith("RELOAD")) {
								var25 = 3;
							} else if (var26.startsWith("Not permitted for social network accounts.")) {
								var25 = 6;
							} else {
								var13.xteaDecryptAll(var10);

								while (var13.offset > 0 && var13.array[var13.offset - 1] == 0) {
									--var13.offset;
								}

								var26 = new String(var13.array, 0, var13.offset);
								if (WorldMapScaleHandler.method3907(var26)) {
									Interpreter.openURL(var26, true, false);
									var25 = 2;
								} else {
									var25 = 5;
								}
							}
							break;
						}

						var13.offset += var19;
						if (var13.offset >= 1000) {
							var25 = 5;
							break;
						}
					}
				} catch (Throwable var27) {
					var27.printStackTrace();
					var25 = 5;
				}

				var0 = var25;
			}

			switch(var0) {
			case 2:
				PlatformInfo.setLoginResponseString(Strings.field3609, Strings.field3610, Strings.field3611);
				Message.method1113(6);
				break;
			case 3:
				PlatformInfo.setLoginResponseString("", "Error connecting to server.", "");
				break;
			case 4:
				PlatformInfo.setLoginResponseString("The part of the website you are trying", "to connect to is offline at the moment.", "Please try again later.");
				break;
			case 5:
				PlatformInfo.setLoginResponseString("Sorry, there was an error trying to", "log you in to this part of the website.", "Please try again later.");
				break;
			case 6:
				PlatformInfo.setLoginResponseString("", "Error connecting to server.", "");
				break;
			case 7:
				PlatformInfo.setLoginResponseString("You must enter a valid login to proceed. For accounts", "created after 24th November 2010, please use your", "email address. Otherwise please use your username.");
			}

		}
	}

	@ObfuscatedName("v")
	@ObfuscatedSignature(
		descriptor = "(Lbh;S)V",
		garbageValue = "28449"
	)
	@Export("changeWorld")
	static void changeWorld(World var0) {
		if (var0.isMembersOnly() != Client.isMembersWorld) {
			Client.isMembersWorld = var0.isMembersOnly();
			boolean var1 = var0.isMembersOnly();
			if (var1 != Huffman.ItemDefinition_inMembersWorld) {
				Client.method1330();
				Huffman.ItemDefinition_inMembersWorld = var1;
			}
		}

		if (var0.properties != Client.worldProperties) {
			Archive var3 = World.archive8;
			int var2 = var0.properties;
			if ((var2 & 536870912) != 0) {
				class394.logoSprite = class196.SpriteBuffer_getIndexedSpriteByName(var3, "logo_deadman_mode", "");
			} else if ((var2 & 1073741824) != 0) {
				class394.logoSprite = class196.SpriteBuffer_getIndexedSpriteByName(var3, "logo_seasonal_mode", "");
			} else {
				class394.logoSprite = class196.SpriteBuffer_getIndexedSpriteByName(var3, "logo", "");
			}
		}

		class279.worldHost = class67.RSPS ? class67.IP : var0.host;
		Client.worldId = var0.id;
		Client.worldProperties = var0.properties;
		GameBuild.worldPort = Client.gameBuild == 0 ? 43594 : var0.id + 40000;
		AbstractWorldMapIcon.js5Port = Client.gameBuild == 0 ? 443 : var0.id + 50000;
		ClientPreferences.currentPort = GameBuild.worldPort;
	}

	@ObfuscatedName("hi")
	@ObfuscatedSignature(
		descriptor = "(Lip;I)V",
		garbageValue = "1826318148"
	)
	static final void method1086(class240 var0) {
		PacketBuffer buf = Client.packetWriter.packetBuffer;
		int a;
		int b;
		int c;
		int d;
		int e;
		int f;
		int offsetY;
		if (class240.field2749 == var0) {
			a = buf.readUnsignedByteS();
			b = a >> 2;
			c = a & 3;
			d = Client.field549[b];
			e = buf.readUnsignedByte();
			f = (e >> 4 & 7) + JagexCache.field1559;
			offsetY = (e & 7) + WorldMapEvent.field2216;
			if (f >= 0 && offsetY >= 0 && f < 104 && offsetY < 104) {
				ParamComposition.updatePendingSpawn(class391.Client_plane, f, offsetY, d, -1, b, c, 0, -1);
			}

		} else {
			int delay;
			int var10;
			int var11;
			int var12;
			int var13;
			byte var37;
			if (class240.field2758 == var0) {
				var37 = buf.readByte();//destXStartX
				b = buf.readUnsignedLEShort();//targetIndex
				c = buf.readUnsignedByteA();//slope
				d = buf.readUnsignedByte();//offsetHash
				e = (d >> 4 & 7) + JagexCache.field1559;//offsetX
				f = (d & 7) + WorldMapEvent.field2216;//offsetY
				offsetY = buf.readUnsignedByteA() * 4;//endHeight
				delay = buf.readUnsignedByteS();//something
				var10 = buf.readShortA();//duration
				var11 = buf.readShortA();//id
				byte var40 = buf.readByteS();//destYStartY
				var13 = buf.readUnsignedByteA() * 4;//startHeight
				int var41 = buf.readShortA();//delay
				a = var37 + e;
				var12 = var40 + f;
				if (e >= 0 && f >= 0 && e < 104 && f < 104 && a >= 0 && var12 >= 0 && a < 104 && var12 < 104 && var11 != 65535) {
					e = e * 128 + 64;
					f = f * 128 + 64;
					a = a * 128 + 64;
					var12 = var12 * 128 + 64;//var10 + Client.cycle, c, delay, b, offsetY
					Projectile projectile = new Projectile(var11, class391.Client_plane, e, f, GraphicsObject.getTileHeight(e, f, class391.Client_plane) - var13, var41 + Client.cycle, var10 + Client.cycle, c, delay, b, offsetY);
					projectile.setDestination(a, var12, GraphicsObject.getTileHeight(a, var12, class391.Client_plane) - offsetY, var41 + Client.cycle);
					Client.projectiles.addFirst(projectile);
				}

			} else {
				if (class240.field2755 == var0) {
					a = buf.readUnsignedShort();
					b = buf.readUnsignedByte();
					c = b >> 4 & 15;
					d = b & 7;
					e = buf.readUnsignedByteS();
					f = (e >> 4 & 7) + JagexCache.field1559;
					offsetY = (e & 7) + WorldMapEvent.field2216;
					delay = buf.readUnsignedByteS();
					if (f >= 0 && offsetY >= 0 && f < 104 && offsetY < 104) {
						var10 = c + 1;
						if (class129.localPlayer.pathX[0] >= f - var10 && class129.localPlayer.pathX[0] <= f + var10 && class129.localPlayer.pathY[0] >= offsetY - var10 && class129.localPlayer.pathY[0] <= var10 + offsetY && VarbitComposition.clientPreferences.areaSoundEffectsVolume != 0 && d > 0 && Client.soundEffectCount < 50) {
							Client.soundEffectIds[Client.soundEffectCount] = a;
							Client.queuedSoundEffectLoops[Client.soundEffectCount] = d;
							Client.queuedSoundEffectDelays[Client.soundEffectCount] = delay;
							Client.soundEffects[Client.soundEffectCount] = null;
							Client.soundLocations[Client.soundEffectCount] = c + (offsetY << 8) + (f << 16);
							++Client.soundEffectCount;
						}
					}
				}

				TileItem var35;
				if (class240.field2752 == var0) {
					a = buf.readUnsignedShort();
					b = buf.readUnsignedShort();
					c = buf.readUnsignedByteA();
					d = (c >> 4 & 7) + JagexCache.field1559;
					e = (c & 7) + WorldMapEvent.field2216;
					if (d >= 0 && e >= 0 && d < 104 && e < 104) {
						var35 = new TileItem();
						var35.id = b;
						var35.quantity = a;
						if (Client.groundItems[class391.Client_plane][d][e] == null) {
							Client.groundItems[class391.Client_plane][d][e] = new NodeDeque();
						}

						Client.groundItems[class391.Client_plane][d][e].addFirst(var35);
						SoundSystem.updateItemPile(d, e);
					}

				} else if (class240.field2751 == var0) {
					a = buf.readShortA();
					b = buf.readUnsignedByte();
					c = (b >> 4 & 7) + JagexCache.field1559;
					d = (b & 7) + WorldMapEvent.field2216;
					if (c >= 0 && d >= 0 && c < 104 && d < 104) {
						NodeDeque var34 = Client.groundItems[class391.Client_plane][c][d];
						if (var34 != null) {
							for (var35 = (TileItem)var34.last(); var35 != null; var35 = (TileItem)var34.previous()) {
								if ((a & 32767) == var35.id) {
									var35.remove();
									break;
								}
							}

							if (var34.last() == null) {
								Client.groundItems[class391.Client_plane][c][d] = null;
							}

							SoundSystem.updateItemPile(c, d);
						}
					}

				} else if (class240.field2750 == var0) {
					a = buf.readUnsignedShort();
					b = buf.readUnsignedByteS();
					c = (b >> 4 & 7) + JagexCache.field1559;
					d = (b & 7) + WorldMapEvent.field2216;
					e = buf.readUnsignedByte();
					f = e >> 2;
					offsetY = e & 3;
					delay = Client.field549[f];
					if (c >= 0 && d >= 0 && c < 104 && d < 104) {
						ParamComposition.updatePendingSpawn(class391.Client_plane, c, d, delay, a, f, offsetY, 0, -1);
					}

				} else if (class240.field2756 == var0) {
					a = buf.readUnsignedByteS();
					b = (a >> 4 & 7) + JagexCache.field1559;
					c = (a & 7) + WorldMapEvent.field2216;
					d = buf.readLEShort();
					e = buf.readUnsignedByteA();
					f = e >> 2;
					offsetY = e & 3;
					delay = Client.field549[f];
					if (b >= 0 && c >= 0 && b < 103 && c < 103) {
						if (delay == 0) {
							BoundaryObject var33 = CollisionMap.scene.method4155(class391.Client_plane, b, c);
							if (var33 != null) {
								var11 = HealthBarDefinition.Entity_unpackID(var33.tag);
								if (f == 2) {
									var33.renderable1 = new DynamicObject(var11, 2, offsetY + 4, class391.Client_plane, b, c, d, false, var33.renderable1);
									var33.renderable2 = new DynamicObject(var11, 2, offsetY + 1 & 3, class391.Client_plane, b, c, d, false, var33.renderable2);
								} else {
									var33.renderable1 = new DynamicObject(var11, f, offsetY, class391.Client_plane, b, c, d, false, var33.renderable1);
								}
							}
						}

						if (delay == 1) {
							WallDecoration var43 = CollisionMap.scene.method4156(class391.Client_plane, b, c);
							if (var43 != null) {
								var11 = HealthBarDefinition.Entity_unpackID(var43.tag);
								if (f != 4 && f != 5) {
									if (f == 6) {
										var43.renderable1 = new DynamicObject(var11, 4, offsetY + 4, class391.Client_plane, b, c, d, false, var43.renderable1);
									} else if (f == 7) {
										var43.renderable1 = new DynamicObject(var11, 4, (offsetY + 2 & 3) + 4, class391.Client_plane, b, c, d, false, var43.renderable1);
									} else if (f == 8) {
										var43.renderable1 = new DynamicObject(var11, 4, offsetY + 4, class391.Client_plane, b, c, d, false, var43.renderable1);
										var43.renderable2 = new DynamicObject(var11, 4, (offsetY + 2 & 3) + 4, class391.Client_plane, b, c, d, false, var43.renderable2);
									}
								} else {
									var43.renderable1 = new DynamicObject(var11, 4, offsetY, class391.Client_plane, b, c, d, false, var43.renderable1);
								}
							}
						}

						if (delay == 2) {
							GameObject var44 = CollisionMap.scene.method4180(class391.Client_plane, b, c);
							if (f == 11) {
								f = 10;
							}

							if (var44 != null) {
								var44.renderable = new DynamicObject(HealthBarDefinition.Entity_unpackID(var44.tag), f, offsetY, class391.Client_plane, b, c, d, false, var44.renderable);
							}
						}

						if (delay == 3) {
							FloorDecoration var45 = CollisionMap.scene.getFloorDecoration(class391.Client_plane, b, c);
							if (var45 != null) {
								var45.renderable = new DynamicObject(HealthBarDefinition.Entity_unpackID(var45.tag), 22, offsetY, class391.Client_plane, b, c, d, false, var45.renderable);
							}
						}
					}

				} else if (class240.field2760 == var0) {
					a = buf.readUnsignedShort();
					b = buf.readShortA();
					c = buf.readUnsignedByteA();
					d = (c >> 4 & 7) + JagexCache.field1559;
					e = (c & 7) + WorldMapEvent.field2216;
					f = buf.readUnsignedByte();
					if (d >= 0 && e >= 0 && d < 104 && e < 104) {
						d = d * 128 + 64;
						e = e * 128 + 64;
						GraphicsObject graphicsObject = new GraphicsObject(b, class391.Client_plane, d, e, GraphicsObject.getTileHeight(d, e, class391.Client_plane) - f, a, Client.cycle);
						Client.graphicsObjects.addFirst(graphicsObject);
					}

				} else {
					if (class240.field2753 == var0) {
						var37 = buf.readByteA();
						b = buf.readUnsignedByte();
						c = (b >> 4 & 7) + JagexCache.field1559;
						d = (b & 7) + WorldMapEvent.field2216;
						e = buf.readShortA();
						byte var38 = buf.readByte();
						offsetY = buf.readUnsignedShort();
						byte var39 = buf.readByteC();
						var10 = buf.readUnsignedByteS();
						var11 = var10 >> 2;
						var12 = var10 & 3;
						var13 = Client.field549[var11];
						byte var14 = buf.readByte();
						int var15 = buf.readLEShortA();
						int var16 = buf.readUnsignedShort();
						Player var17;
						if (var15 == Client.localPlayerIndex) {
							var17 = class129.localPlayer;
						} else {
							var17 = Client.players[var15];
						}

						if (var17 != null) {
							ObjectComposition var18 = HitSplatDefinition.getObjectDefinition(offsetY);
							int var19;
							int var20;
							if (var12 != 1 && var12 != 3) {
								var19 = var18.sizeX;
								var20 = var18.sizeY;
							} else {
								var19 = var18.sizeY;
								var20 = var18.sizeX;
							}

							int var21 = c + (var19 >> 1);
							int var22 = c + (var19 + 1 >> 1);
							int var23 = d + (var20 >> 1);
							int var24 = d + (var20 + 1 >> 1);
							int[][] var25 = Tiles.Tiles_heights[class391.Client_plane];
							int var26 = var25[var21][var23] + var25[var22][var23] + var25[var21][var24] + var25[var22][var24] >> 2;
							int var27 = (c << 7) + (var19 << 6);
							int var28 = (d << 7) + (var20 << 6);
							Model var29 = var18.getModel(var11, var12, var25, var27, var26, var28);
							if (var29 != null) {
								ParamComposition.updatePendingSpawn(class391.Client_plane, c, d, var13, -1, 0, 0, e + 1, var16 + 1);
								var17.animationCycleStart = e + Client.cycle;
								var17.animationCycleEnd = var16 + Client.cycle;
								var17.model0 = var29;
								var17.field1058 = var19 * 64 + c * 128;
								var17.field1060 = var20 * 64 + d * 128;
								var17.tileHeight2 = var26;
								byte var30;
								if (var38 > var37) {
									var30 = var38;
									var38 = var37;
									var37 = var30;
								}

								if (var14 > var39) {
									var30 = var14;
									var14 = var39;
									var39 = var30;
								}

								var17.minX = c + var38;
								var17.maxX = c + var37;
								var17.minY = d + var14;
								var17.maxY = d + var39;
							}
						}
					}

					if (class240.field2757 == var0) {
						a = buf.readShortA();
						b = buf.readShortA();
						c = buf.readShortA();
						d = buf.readUnsignedByteC();
						e = (d >> 4 & 7) + JagexCache.field1559;
						f = (d & 7) + WorldMapEvent.field2216;
						if (e >= 0 && f >= 0 && e < 104 && f < 104) {
							NodeDeque var31 = Client.groundItems[class391.Client_plane][e][f];
							if (var31 != null) {
								for (TileItem var32 = (TileItem)var31.last(); var32 != null; var32 = (TileItem)var31.previous()) {
									if ((c & 32767) == var32.id && b == var32.quantity) {
										var32.quantity = a;
										break;
									}
								}

								SoundSystem.updateItemPile(e, f);
							}
						}

					}
				}
			}
		}
	}
}
