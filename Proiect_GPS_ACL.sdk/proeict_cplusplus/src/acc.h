#include <stdio.h>
#include <stdlib.h>
#include "PmodACL2.h"
#include "sleep.h"
#include "xil_cache.h"
#include "xil_printf.h"
#include "xparameters.h"

char * GetDataFromACL();
char * to_send  = (char*) malloc(sizeof(char*) * 1024);;

int status;

PmodACL2 myDevice;
ACL2_SamplePacket data;


void InitACL() {
	ACL2_begin(&myDevice, XPAR_PMODACL2_0_AXI_LITE_GPIO_BASEADDR,XPAR_PMODACL2_0_AXI_LITE_SPI_BASEADDR);
	ACL2_reset(&myDevice);
	usleep(1000);
	ACL2_configure(&myDevice);
}

char * GetDataFromACL() {

   while (1) {
      status = ACL2_getStatus(&myDevice);

      if ((ACL2_STATUS_DATA_READY_MASK & status) != 0) {
         data = ACL2_getSample(&myDevice);
         sprintf(to_send,"X:%f ,Y:%f ,Z:%f\n",ACL2_DataToG(&myDevice, data.XData),ACL2_DataToG(&myDevice, data.YData),ACL2_DataToG(&myDevice, data.ZData));
         break;
      }
   }
   return to_send;
}

//to end process : ACL2_end(&myDevice);

