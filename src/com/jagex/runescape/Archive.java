package com.jagex.runescape;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.jagex.runescape.cache.bzip.BZip2Decompressor;

public class Archive {

	public Archive(byte _data[]) {
		init(_data);
	}

	public void init(byte _data[]) {
		Buffer buf = new Buffer(_data);
		int extractedSize = buf.get24BitInt();
		int size = buf.get24BitInt();
		if (size != extractedSize) {
			byte extractedBuf[] = new byte[extractedSize];
			BZip2Decompressor.decompress(extractedBuf, extractedSize, _data, size, 6);
			data = extractedBuf;
			buf = new Buffer(data);
			extracted = true;
		} else {
			data = _data;
			extracted = false;
		}
		entries = buf.getUnsignedLEShort();
		hashes = new int[entries];
		extractedSizes = new int[entries];
		sizes = new int[entries];
		offsets = new int[entries];
		int offset = buf.offset + entries * 10;
		for (int pos = 0; pos < entries; pos++) {
			hashes[pos] = buf.getInt();
			extractedSizes[pos] = buf.get24BitInt();
			sizes[pos] = buf.get24BitInt();
			offsets[pos] = offset;
			offset += sizes[pos];
		}
	}

	public byte[] getFile(String name) {
		byte[] dest = null;
		int hash = 0;
		name = name.toUpperCase();

		for (int pos = 0; pos < name.length(); pos++)
			hash = (hash * 61 + name.charAt(pos)) - 32;

		for (int entry = 0; entry < entries; entry++) {
			if (hashes[entry] == hash) {
				if (dest == null)
					dest = new byte[extractedSizes[entry]];

				if (!extracted) {
					BZip2Decompressor.decompress(dest, extractedSizes[entry], data, sizes[entry], offsets[entry]);
				} else {
					for (int pos = 0; pos < extractedSizes[entry]; pos++)
						dest[pos] = data[offsets[entry] + pos];

				}
				return dest;
			}
		}
		return null;
	}
	
	public byte data[];
	public int entries;
	public int hashes[];
	public int extractedSizes[];
	public int sizes[];
	public int offsets[];
	public boolean extracted;
}