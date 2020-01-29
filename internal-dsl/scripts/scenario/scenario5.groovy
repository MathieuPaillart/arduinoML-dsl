sensor "button" onPin 9
lcd "ecranLCD" onBus 1

state "write" means ecranLCD prints button

initial write

from write to write

export "lcdPrintSensor"
