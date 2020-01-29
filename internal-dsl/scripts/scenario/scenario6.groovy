sensor "button" onPin 9
actuator "led" pin 10
lcd "ecranLCD" onBus 1
keyboard "k"

state "write" means led becomes LOW and ecranLCD prints k   //Ecran lcd prend la valeur du keyboard
state "wait" means led becomes HIGH
initial write

from write to wait
from wait to write when button becomes HIGH

export "lcdPrintKeyboardValue"
