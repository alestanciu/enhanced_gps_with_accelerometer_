#include "xuartlite.h"
#include <xuartlite_l.h>
#include <stdio.h>
#include <string.h>
#include <cstdlib>

using namespace std;

XUartLite UartLite;
u8 ReceiveBuffer[100];
int Index=0,Secure_Bytes_Lines = 0;

void Init_GPS()
{
	XUartLite_Initialize(&UartLite, XPAR_UARTLITE_0_DEVICE_ID);
}

char * RecieveGPSData()
{
	Index = 0;

	memset(ReceiveBuffer, 0, sizeof(ReceiveBuffer));
	while((ReceiveBuffer[Index] = XUartLite_RecvByte(XPAR_UARTLITE_0_BASEADDR)))
	{
		if(Index>0)
		{
			if((ReceiveBuffer[Index-1]==0x0D) && (ReceiveBuffer[Index]==0x0A)) break;
		}
	Index++;
	}
	ReceiveBuffer[Index-1]='\0';
	//xil_printf("%s\n",(char*)ReceiveBuffer,strlen((char*)ReceiveBuffer));

	return (char*)ReceiveBuffer;
}
