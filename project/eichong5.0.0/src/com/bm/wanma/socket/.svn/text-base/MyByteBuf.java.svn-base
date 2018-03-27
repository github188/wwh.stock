package com.bm.wanma.socket;

//ByteBuf接口 

public class MyByteBuf {

	private static int MAX_CAPACITY = 4096; // 缓存大小
	private static byte[] buffer = new byte[MAX_CAPACITY]; // 缓存区

	private static int readerIndex;// 缓存中读位置
	private static int writeIndex;// 缓存中写位置

	private static int readerNum;// 标记前一次读了多少字节，回退读位置使用

	// 清除缓存
	public static void clear() {
		readerIndex = 0;
		writeIndex = 0;
	}

	// 缓存中未读取的字节数
	public static int readableBytes() {
		if (writeIndex >= readerIndex) {
			return writeIndex - readerIndex;
		}else {
			return (MAX_CAPACITY - readerIndex) + writeIndex;
		}

	}

	// 在缓存中查找报文头
	public static int bytesBefore(byte b) {
		if (readableBytes() > 0) {
			if (readerIndex > writeIndex) {
				for (int i = readerIndex; i < MAX_CAPACITY; i++) {
					if (buffer[i] == b)
						return i - readerIndex;
				}
				for (int i = 0; i < writeIndex; i++) {
					if (buffer[i] == b)
						return MAX_CAPACITY - readerIndex + i;
				}
			}
			for (int i = readerIndex; i < writeIndex; i++) {
				if (buffer[i] == b)
					return i - readerIndex;
			}
		}

		return -1;
	}

	// 用户readBytes一次后，回退readBytes之前的读位置
	public static boolean resetReaderIndex() {
		if (readerNum <= 0)
			return false;
		readerIndex = (readerIndex + MAX_CAPACITY - readerNum) % MAX_CAPACITY;
		return true;
	}

	// 读出缓存中读数据
	public static int readBytes(byte[] hasMsg) {
		if (hasMsg == null)
			return 0;

		int realLen = readableBytes();
		realLen = (realLen >= hasMsg.length ? hasMsg.length : realLen);
		readerNum = realLen;
		int hasLen = 0;
		int remainLen = 0;
		if (readerIndex > writeIndex) {
			hasLen = MAX_CAPACITY - readerIndex;
			System.arraycopy(buffer, readerIndex, hasMsg, 0, hasLen);
			remainLen = realLen - hasLen;
			System.arraycopy(buffer, 0, hasMsg, hasLen, remainLen);

			readerIndex = (readerIndex + hasLen + remainLen) % MAX_CAPACITY;

			return realLen;
		}
		System.arraycopy(buffer, readerIndex, hasMsg, 0, realLen);
		readerIndex = (readerIndex + realLen) % MAX_CAPACITY;

		return realLen;
	}

	// 写入缓存
	public static int writeBytes(byte[] src) {
		if (src == null || src.length <= 0)
			return 0;
		int hasLen = 0;
		int remainLen = 0;
		int len = src.length;
		//需要环绕一遍
		if (writeIndex + len > MAX_CAPACITY) {
			hasLen = MAX_CAPACITY - writeIndex;
			if (writeIndex < readerIndex) {
				hasLen = readerIndex - writeIndex;
				// 剩余没拷贝的字节数
				remainLen = 0;
			} else {
				remainLen = (len - hasLen >= readerIndex ? readerIndex : len
						- hasLen);
			}

			System.arraycopy(src, 0, buffer, writeIndex, hasLen);
			writeIndex = (writeIndex + hasLen) % MAX_CAPACITY;
			if (remainLen > 0) {
				System.arraycopy(src, hasLen, buffer, 0, remainLen);
				writeIndex = remainLen % MAX_CAPACITY;
			}
			return remainLen + hasLen;
		} else {
			if (writeIndex < readerIndex) {
				hasLen = readerIndex - writeIndex;
				if(hasLen > len){
					hasLen = len;
				}
			} else {
				hasLen = len;
			}
			System.arraycopy(src, 0, buffer, writeIndex, hasLen);
			writeIndex = (writeIndex + len) % MAX_CAPACITY;

		}
		return hasLen;
	}

	// 读一个字节
	public static Byte readByte() {
		Byte btye = 0;
		int realLen = readableBytes();
		if (realLen > 0) {
			btye = buffer[readerIndex];
			readerNum = 1;
			readerIndex = (readerIndex + 1) % MAX_CAPACITY;
		}
		return btye;
	}

}