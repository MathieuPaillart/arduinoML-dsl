sensor "button" onPin 9
lcd "ecranLCD" onBus 2

state "write" means ecranLCD prints button

initial write

from write to write

export "lcdDo"
