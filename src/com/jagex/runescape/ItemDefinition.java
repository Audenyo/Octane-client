package com.jagex.runescape;

import com.jagex.runescape.cache.Archive;
import com.jagex.runescape.collection.Cache;
import com.jagex.runescape.media.Rasterizer;
import com.jagex.runescape.media.Rasterizer3D;
import com.jagex.runescape.media.renderable.Model;
import com.jagex.runescape.media.renderable.Renderable;
import com.jagex.runescape.net.Buffer;

public class ItemDefinition {

	public int anInt326;
	public int modelOffsetX;
	public byte description[];
	public String name;
	public byte aByte330;
	public int anInt331;
	public int team;
	public int notedInfoId;
	public int anInt334;
	public static int count;
	public static ItemDefinition cache[];
	public static Cache modelCache = new Cache(50);
	public String groundActions[];
	public int anInt339;
	public int modelOffsetY;
	public int destColors[];
	public static int indices[];
	public int notedGraphicsId;
	public static boolean memberServer = true;
	public int value;
	public static Cache rgbImageCache = new Cache(100);
	public String inventoryActions[];
	public static boolean aBoolean350 = true;
	public static int cachePos;
	public int anInt353;
	public int anInt354;
	public int anInt355;
	public int modelRotationY;
	public int anInt357;
	public int anInt358;
	public int modelRotationX;
	public int modelId;
	public int anInt361;
	public int anInt362;
	public int id = -1;
	public int srcColors[];
	public int stackIds[];
	public int anInt366;
	public int anInt367;
	public int anInt368;
	public int modelScale;
	public int anInt370;
	public boolean stackable;
	public int anInt372;
	public static Buffer buffer;
	public int anInt375;
	public int stackAmounts[];
	public boolean members;
	public byte aByte378;


	public void reset() {
		modelId = 0;
		name = null;
		description = null;
		srcColors = null;
		destColors = null;
		modelScale = 2000;
		modelRotationX = 0;
		modelRotationY = 0;
		anInt339 = 0;
		modelOffsetX = 0;
		modelOffsetY = 0;
		anInt372 = -1;
		stackable = false;
		value = 1;
		members = false;
		groundActions = null;
		inventoryActions = null;
		anInt353 = -1;
		anInt331 = -1;
		aByte378 = 0;
		anInt326 = -1;
		anInt355 = -1;
		aByte330 = 0;
		anInt370 = -1;
		anInt367 = -1;
		anInt334 = -1;
		anInt361 = -1;
		anInt375 = -1;
		anInt362 = -1;
		stackIds = null;
		stackAmounts = null;
		notedInfoId = -1;
		notedGraphicsId = -1;
		anInt366 = 128;
		anInt357 = 128;
		anInt368 = 128;
		anInt354 = 0;
		anInt358 = 0;
		team = 0;
	}

	public boolean isDialogueCached(int i) {
		int k = anInt334;
		int l = anInt361;
		if (i == 1) {
			k = anInt375;
			l = anInt362;
		}
		if (k == -1)
			return true;
		boolean flag = true;
		if (!Model.isCached(k))
			flag = false;
		if (l != -1 && !Model.isCached(l))
			flag = false;
		return flag;
	}
	
	public static ItemDefinition forId(int id) {
		for (int j = 0; j < 10; j++)
			if (cache[j].id == id)
				return cache[j];

		cachePos = (cachePos + 1) % 10;
		ItemDefinition def = cache[cachePos];
		buffer.currentPosition = indices[id];
		def.id = id;
		def.reset();
		def.init(buffer);
		if (def.notedGraphicsId != -1)
			def.toNote();
		if (!memberServer && def.members) {
			def.name = "Members Object";
			def.description = "Login to a members' server to use this object.".getBytes();
			def.groundActions = null;
			def.inventoryActions = null;
			def.team = 0;
		}
		return def;
	}

	public Model method213(byte byte0, int i) {
		int j = anInt353;
		int k = anInt331;
		int l = anInt370;
		if (i == 1) {
			j = anInt326;
			k = anInt355;
			l = anInt367;
		}
		if (j == -1)
			return null;
		Model class50_sub1_sub4_sub4 = Model.getModel(j);
		if (byte0 != -98)
			throw new NullPointerException();
		if (k != -1)
			if (l != -1) {
				Model class50_sub1_sub4_sub4_1 = Model.getModel(k);
				Model class50_sub1_sub4_sub4_3 = Model.getModel(l);
				Model aclass50_sub1_sub4_sub4_1[] = { class50_sub1_sub4_sub4,
						class50_sub1_sub4_sub4_1, class50_sub1_sub4_sub4_3 };
				class50_sub1_sub4_sub4 = new Model(3, aclass50_sub1_sub4_sub4_1);
			} else {
				Model class50_sub1_sub4_sub4_2 = Model.getModel(k);
				Model aclass50_sub1_sub4_sub4[] = { class50_sub1_sub4_sub4, class50_sub1_sub4_sub4_2 };
				class50_sub1_sub4_sub4 = new Model(2, aclass50_sub1_sub4_sub4);
			}
		if (i == 0 && aByte378 != 0)
			class50_sub1_sub4_sub4.translate(0, 0, aByte378);
		if (i == 1 && aByte330 != 0)
			class50_sub1_sub4_sub4.translate(0, 0, aByte330);
		if (srcColors != null) {
			for (int color = 0; color < srcColors.length; color++)
				class50_sub1_sub4_sub4.replaceColor(srcColors[color], destColors[color]);

		}
		return class50_sub1_sub4_sub4;
	}

	public static void load(Archive archive) {
		buffer = new Buffer(archive.getFile("obj.dat"));
		Buffer objectIndexVector = new Buffer(archive.getFile("obj.idx"));
		count = objectIndexVector.getUnsignedLEShort();
		indices = new int[count];
		int index = 2;
		for (int j = 0; j < count; j++) {
			indices[j] = index;
			index += objectIndexVector.getUnsignedLEShort();
		}

		cache = new ItemDefinition[10];
		for (int k = 0; k < 10; k++)
			cache[k] = new ItemDefinition();

	}

	public void toNote() {
		ItemDefinition graphics = forId(notedGraphicsId);
		modelId = graphics.modelId;
		modelScale = graphics.modelScale;
		modelRotationX = graphics.modelRotationX;
		modelRotationY = graphics.modelRotationY;
		anInt339 = graphics.anInt339;
		modelOffsetX = graphics.modelOffsetX;
		modelOffsetY = graphics.modelOffsetY;
		srcColors = graphics.srcColors;
		destColors = graphics.destColors;
		ItemDefinition info = forId(notedInfoId);
		name = info.name;
		members = info.members;
		value = info.value;
		String prefix = "a";
		char firstChar = info.name.charAt(0);
		if (firstChar == 'A' || firstChar == 'E' || firstChar == 'I' || firstChar == 'O' || firstChar == 'U')
			prefix = "an";
		description = ("Swap this note at any bank for " + prefix + " " + info.name + ".").getBytes();
		stackable = true;
	}

	public boolean equipmentReady(int i, int j) {
		while (i >= 0)
			aBoolean350 = !aBoolean350;
		int k = anInt353;
		int l = anInt331;
		int i1 = anInt370;
		if (j == 1) {
			k = anInt326;
			l = anInt355;
			i1 = anInt367;
		}
		if (k == -1)
			return true;
		boolean flag = true;
		if (!Model.isCached(k))
			flag = false;
		if (l != -1 && !Model.isCached(l))
			flag = false;
		if (i1 != -1 && !Model.isCached(i1))
			flag = false;
		return flag;
	}

	public Model getUncachedModel(int amount) {
		if (stackIds != null && amount > 1) {
			int stackId = -1;
			for (int l = 0; l < 10; l++)
				if (amount >= stackAmounts[l] && stackAmounts[l] != 0)
					stackId = stackIds[l];

			if (stackId != -1)
				return forId(stackId).getUncachedModel(1);
		}
		Model class50_sub1_sub4_sub4 = Model.getModel(modelId);
		if (class50_sub1_sub4_sub4 == null)
			return null;
		if (srcColors != null) {
			for (int i1 = 0; i1 < srcColors.length; i1++)
				class50_sub1_sub4_sub4.replaceColor(srcColors[i1], destColors[i1]);

		}
		return class50_sub1_sub4_sub4;
	}

	public void init(Buffer vec) {
		do {
			int i = vec.getUnsignedByte();
			if (i == 0)
				return;
			if (i == 1)
				modelId = vec.getUnsignedLEShort();
			else if (i == 2)
				name = vec.getString();
			else if (i == 3)
				description = vec.getStringBytes();
			else if (i == 4)
				modelScale = vec.getUnsignedLEShort();
			else if (i == 5)
				modelRotationX = vec.getUnsignedLEShort();
			else if (i == 6)
				modelRotationY = vec.getUnsignedLEShort();
			else if (i == 7) {
				modelOffsetX = vec.getUnsignedLEShort();
				if (modelOffsetX > 32767)
					modelOffsetX -= 0x10000;
			} else if (i == 8) {
				modelOffsetY = vec.getUnsignedLEShort();
				if (modelOffsetY > 32767)
					modelOffsetY -= 0x10000;
			} else if (i == 10)
				anInt372 = vec.getUnsignedLEShort();
			else if (i == 11)
				stackable = true;
			else if (i == 12)
				value = vec.getInt();
			else if (i == 16)
				members = true;
			else if (i == 23) {
				anInt353 = vec.getUnsignedLEShort();
				aByte378 = vec.getSignedByte();
			} else if (i == 24)
				anInt331 = vec.getUnsignedLEShort();
			else if (i == 25) {
				anInt326 = vec.getUnsignedLEShort();
				aByte330 = vec.getSignedByte();
			} else if (i == 26)
				anInt355 = vec.getUnsignedLEShort();
			else if (i >= 30 && i < 35) {
				if (groundActions == null)
					groundActions = new String[5];
				groundActions[i - 30] = vec.getString();
				if (groundActions[i - 30].equalsIgnoreCase("hidden"))
					groundActions[i - 30] = null;
			} else if (i >= 35 && i < 40) {
				if (inventoryActions == null)
					inventoryActions = new String[5];
				inventoryActions[i - 35] = vec.getString();
			} else if (i == 40) {
				int colorCount = vec.getUnsignedByte();
				srcColors = new int[colorCount];
				destColors = new int[colorCount];
				for (int k = 0; k < colorCount; k++) {
					srcColors[k] = vec.getUnsignedLEShort();
					destColors[k] = vec.getUnsignedLEShort();
				}

			} else if (i == 78)
				anInt370 = vec.getUnsignedLEShort();
			else if (i == 79)
				anInt367 = vec.getUnsignedLEShort();
			else if (i == 90)
				anInt334 = vec.getUnsignedLEShort();
			else if (i == 91)
				anInt375 = vec.getUnsignedLEShort();
			else if (i == 92)
				anInt361 = vec.getUnsignedLEShort();
			else if (i == 93)
				anInt362 = vec.getUnsignedLEShort();
			else if (i == 95)
				anInt339 = vec.getUnsignedLEShort();
			else if (i == 97)
				notedInfoId = vec.getUnsignedLEShort();
			else if (i == 98)
				notedGraphicsId = vec.getUnsignedLEShort();
			else if (i >= 100 && i < 110) {
				if (stackIds == null) {
					stackIds = new int[10];
					stackAmounts = new int[10];
				}
				stackIds[i - 100] = vec.getUnsignedLEShort();
				stackAmounts[i - 100] = vec.getUnsignedLEShort();
			} else if (i == 110)
				anInt366 = vec.getUnsignedLEShort();
			else if (i == 111)
				anInt357 = vec.getUnsignedLEShort();
			else if (i == 112)
				anInt368 = vec.getUnsignedLEShort();
			else if (i == 113)
				anInt354 = vec.getSignedByte();
			else if (i == 114)
				anInt358 = vec.getSignedByte() * 5;
			else if (i == 115)
				team = vec.getUnsignedByte();
		} while (true);
	}

	public Model getGenderModel(int gender) {
		int j = anInt334;
		int k = anInt361;
		if (gender == 1) {
			j = anInt375;
			k = anInt362;
		}
		if (j == -1)
			return null;
		Model class50_sub1_sub4_sub4 = Model.getModel(j);
		if (k != -1) {
			Model class50_sub1_sub4_sub4_1 = Model.getModel(k);
			Model aclass50_sub1_sub4_sub4[] = { class50_sub1_sub4_sub4, class50_sub1_sub4_sub4_1 };
			class50_sub1_sub4_sub4 = new Model(2, aclass50_sub1_sub4_sub4);
		}
		if (srcColors != null) {
			for (int l = 0; l < srcColors.length; l++)
				class50_sub1_sub4_sub4.replaceColor(srcColors[l], destColors[l]);

		}
		return class50_sub1_sub4_sub4;
	}

	public Model getModel(int amount) {
		if (stackIds != null && amount > 1) {
			int stackId = -1;
			for (int pos = 0; pos < 10; pos++)
				if (amount >= stackAmounts[pos] && stackAmounts[pos] != 0)
					stackId = stackIds[pos];

			if (stackId != -1)
				return forId(stackId).getModel(1);
		}
		Model model = (Model) modelCache.get(id);
		if (model != null)
			return model;
		model = Model.getModel(modelId);
		if (model == null)
			return null;
		if (anInt366 != 128 || anInt357 != 128 || anInt368 != 128)
			model.scaleT(anInt357, anInt368, 9, anInt366);
		if (srcColors != null) {
			for (int l = 0; l < srcColors.length; l++)
				model.replaceColor(srcColors[l], destColors[l]);

		}
		model.applyLighting(64 + anInt354, 768 + anInt358, -50, -10, -50, true);
		model.oneSquareModel = true;
		modelCache.put(model, id);
		return model;
	}

	public static ImageRGB method221(byte byte0, int i, int j, int k) {
		if (i == 0) {
			ImageRGB class50_sub1_sub1_sub1 = (ImageRGB) rgbImageCache.get(k);
			if (class50_sub1_sub1_sub1 != null && class50_sub1_sub1_sub1.anInt1495 != j
					&& class50_sub1_sub1_sub1.anInt1495 != -1) {
				class50_sub1_sub1_sub1.remove();
				class50_sub1_sub1_sub1 = null;
			}
			if (class50_sub1_sub1_sub1 != null)
				return class50_sub1_sub1_sub1;
		}
		ItemDefinition class16 = forId(k);
		if (class16.stackIds == null)
			j = -1;
		if (j > 1) {
			int l = -1;
			for (int i1 = 0; i1 < 10; i1++)
				if (j >= class16.stackAmounts[i1] && class16.stackAmounts[i1] != 0)
					l = class16.stackIds[i1];

			if (l != -1)
				class16 = forId(l);
		}
		Model class50_sub1_sub4_sub4 = class16.getModel(1);
		if (class50_sub1_sub4_sub4 == null)
			return null;
		ImageRGB class50_sub1_sub1_sub1_2 = null;
		if (class16.notedGraphicsId != -1) {
			class50_sub1_sub1_sub1_2 = method221((byte) -33, -1, 10, class16.notedInfoId);
			if (class50_sub1_sub1_sub1_2 == null)
				return null;
		}
		ImageRGB class50_sub1_sub1_sub1_1 = new ImageRGB(32, 32);
		int j1 = Rasterizer3D.centerX;
		int k1 = Rasterizer3D.centerY;
		int ai[] = Rasterizer3D.lineOffsets;
		int ai1[] = Rasterizer.pixels;
		int l1 = Rasterizer.width;
		int i2 = Rasterizer.height;
		int j2 = Rasterizer.topX;
		int k2 = Rasterizer.bottomX;
		int l2 = Rasterizer.topY;
		int i3 = Rasterizer.bottomY;
		Rasterizer3D.aBoolean1530 = false;
		Rasterizer.createRasterizer(class50_sub1_sub1_sub1_1.pixels, 32, 32);
		Rasterizer.drawFilledRectangle(0, 0, 32, 32, 0);
		Rasterizer3D.setDefaultBoundaries();
		int j3 = class16.modelScale;
		if (i == -1)
			j3 = (int) (j3 * 1.5D);
		if (i > 0)
			j3 = (int) (j3 * 1.04D);
		int k3 = Rasterizer3D.SINE[class16.modelRotationX] * j3 >> 16;
		int l3 = Rasterizer3D.COSINE[class16.modelRotationX] * j3 >> 16;
		class50_sub1_sub4_sub4.method598(0, class16.modelRotationY, class16.anInt339, class16.modelRotationX, class16.modelOffsetX, k3
				+ ((Renderable) (class50_sub1_sub4_sub4)).modelHeight / 2 + class16.modelOffsetY, l3
				+ class16.modelOffsetY);
		for (int l4 = 31; l4 >= 0; l4--) {
			for (int i4 = 31; i4 >= 0; i4--)
				if (class50_sub1_sub1_sub1_1.pixels[l4 + i4 * 32] == 0)
					if (l4 > 0 && class50_sub1_sub1_sub1_1.pixels[(l4 - 1) + i4 * 32] > 1)
						class50_sub1_sub1_sub1_1.pixels[l4 + i4 * 32] = 1;
					else if (i4 > 0 && class50_sub1_sub1_sub1_1.pixels[l4 + (i4 - 1) * 32] > 1)
						class50_sub1_sub1_sub1_1.pixels[l4 + i4 * 32] = 1;
					else if (l4 < 31 && class50_sub1_sub1_sub1_1.pixels[l4 + 1 + i4 * 32] > 1)
						class50_sub1_sub1_sub1_1.pixels[l4 + i4 * 32] = 1;
					else if (i4 < 31 && class50_sub1_sub1_sub1_1.pixels[l4 + (i4 + 1) * 32] > 1)
						class50_sub1_sub1_sub1_1.pixels[l4 + i4 * 32] = 1;

		}

		if (i > 0) {
			for (int i5 = 31; i5 >= 0; i5--) {
				for (int j4 = 31; j4 >= 0; j4--)
					if (class50_sub1_sub1_sub1_1.pixels[i5 + j4 * 32] == 0)
						if (i5 > 0 && class50_sub1_sub1_sub1_1.pixels[(i5 - 1) + j4 * 32] == 1)
							class50_sub1_sub1_sub1_1.pixels[i5 + j4 * 32] = i;
						else if (j4 > 0 && class50_sub1_sub1_sub1_1.pixels[i5 + (j4 - 1) * 32] == 1)
							class50_sub1_sub1_sub1_1.pixels[i5 + j4 * 32] = i;
						else if (i5 < 31 && class50_sub1_sub1_sub1_1.pixels[i5 + 1 + j4 * 32] == 1)
							class50_sub1_sub1_sub1_1.pixels[i5 + j4 * 32] = i;
						else if (j4 < 31 && class50_sub1_sub1_sub1_1.pixels[i5 + (j4 + 1) * 32] == 1)
							class50_sub1_sub1_sub1_1.pixels[i5 + j4 * 32] = i;

			}

		} else if (i == 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--)
					if (class50_sub1_sub1_sub1_1.pixels[j5 + k4 * 32] == 0 && j5 > 0 && k4 > 0
							&& class50_sub1_sub1_sub1_1.pixels[(j5 - 1) + (k4 - 1) * 32] > 0)
						class50_sub1_sub1_sub1_1.pixels[j5 + k4 * 32] = 0x302020;

			}

		}
		if (class16.notedGraphicsId != -1) {
			int k5 = class50_sub1_sub1_sub1_2.anInt1494;
			int l5 = class50_sub1_sub1_sub1_2.anInt1495;
			class50_sub1_sub1_sub1_2.anInt1494 = 32;
			class50_sub1_sub1_sub1_2.anInt1495 = 32;
			class50_sub1_sub1_sub1_2.method461(0, 0, -488);
			class50_sub1_sub1_sub1_2.anInt1494 = k5;
			class50_sub1_sub1_sub1_2.anInt1495 = l5;
		}
		if (i == 0)
			rgbImageCache.put(class50_sub1_sub1_sub1_1, k);
		Rasterizer.createRasterizer(ai1, l1, i2);
		Rasterizer.setCoordinates(l2, j2, i3, k2);
		Rasterizer3D.centerX = j1;
		Rasterizer3D.centerY = k1;
		Rasterizer3D.lineOffsets = ai;
		Rasterizer3D.aBoolean1530 = true;
		if (class16.stackable)
			class50_sub1_sub1_sub1_1.anInt1494 = 33;
		else
			class50_sub1_sub1_sub1_1.anInt1494 = 32;
		class50_sub1_sub1_sub1_1.anInt1495 = j;
		if (byte0 != -33)
			throw new NullPointerException();
		else
			return class50_sub1_sub1_sub1_1;
	}

	public static void method222(boolean flag) {
		modelCache = null;
		if (flag) {
			for (int i = 1; i > 0; i++);
		}
		rgbImageCache = null;
		indices = null;
		cache = null;
		buffer = null;
	}


}
