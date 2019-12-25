package com.tmri.rfid.util;

public class SAM_BINDRECV_FRAME
{
    public byte header;//同步头0xaa
    public byte frameType;//帧类型0x31
    public short frameLength;//帧长度:0x  
    public byte operateId;//操作标识:3

    public short transmitLength;//转发数据长度
    public byte transmitMethod;//传输方式：大小端
    public byte[] deviceSerial = new byte[8];
    public byte[] reserve = new byte[13];
    public byte[] samdeviceSerial = new byte[16];//允许绑定的安全模块序列号+读写设备序列号
    public byte[] bindStream = new byte[8];//绑定流水号
    public byte[] bindReserve = new byte[8];//预留
    public byte[] sign = new byte[64];//签名
    public byte[] C1 = new byte[64];//
    public byte[] C3 = new byte[32];//

    public byte checkSum;//帧校验
    
    
    public byte[] toBytes()
    {
    	int size = 1+1+2+1+2+1+8+13+16+8+8+64+64+32+1;
        byte[] bytes = new byte[size];
        
        bytes[0] = header;
        bytes[1] = frameType;
		EriUtil.shortToBytes(bytes,frameLength,2);
        bytes[4] = operateId;
        EriUtil.shortToBytes(bytes,transmitLength,5);
        bytes[7] = transmitMethod;
		System.arraycopy(deviceSerial,0,bytes,8,8);
		System.arraycopy(reserve,0,bytes,16,13);
		System.arraycopy(samdeviceSerial,0,bytes,29,16);
		System.arraycopy(bindStream,0,bytes,45,8);
		System.arraycopy(bindReserve,0,bytes,53,8);
		System.arraycopy(sign,0,bytes,61,64);
		System.arraycopy(C1,0,bytes,125,64);
		System.arraycopy(C3,0,bytes,189,32);
		bytes[221] = checkSum;
        
        //IntPtr structPtr = Marshal.AllocHGlobal(size);
        //将结构体拷到分配好的内存空间
        //Marshal.StructureToPtr(structObj, structPtr, false);
        //从内存空间拷贝到byte 数组
        //Marshal.Copy(structPtr, bytes, 0, size);
        //释放内存空间
        //Marshal.FreeHGlobal(structPtr);
        return bytes;
    }
}
