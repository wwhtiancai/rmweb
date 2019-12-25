package com.tmri.rfid.util;

public class SAM_BINDRECV_FRAME
{
    public byte header;//ͬ��ͷ0xaa
    public byte frameType;//֡����0x31
    public short frameLength;//֡����:0x  
    public byte operateId;//������ʶ:3

    public short transmitLength;//ת�����ݳ���
    public byte transmitMethod;//���䷽ʽ����С��
    public byte[] deviceSerial = new byte[8];
    public byte[] reserve = new byte[13];
    public byte[] samdeviceSerial = new byte[16];//����󶨵İ�ȫģ�����к�+��д�豸���к�
    public byte[] bindStream = new byte[8];//����ˮ��
    public byte[] bindReserve = new byte[8];//Ԥ��
    public byte[] sign = new byte[64];//ǩ��
    public byte[] C1 = new byte[64];//
    public byte[] C3 = new byte[32];//

    public byte checkSum;//֡У��
    
    
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
        //���ṹ�忽������õ��ڴ�ռ�
        //Marshal.StructureToPtr(structObj, structPtr, false);
        //���ڴ�ռ俽����byte ����
        //Marshal.Copy(structPtr, bytes, 0, size);
        //�ͷ��ڴ�ռ�
        //Marshal.FreeHGlobal(structPtr);
        return bytes;
    }
}
